package team_p1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

class CalendarDataManager{ // 6*7�迭�� ��Ÿ�� �޷� ���� ���ϴ� class
    static final int CAL_WIDTH = 7;																				//�迭 �� ����(��ȭ���������)
    final static int CAL_HEIGHT = 6;																			//�迭 �� ����(6��)
    int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];									//���� int
    int calYear;																													//��� int
    int calMonth;																												//���	int
    int calDayOfMon;																										//
    final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};			//���� ������ int �迭
    int calLastDate;
    Calendar today = Calendar.getInstance();																//Calendar�� today ����(Calendar ���� ������) ->  Today �����				
    Calendar cal;
    public CalendarDataManager(){
        setToday();																				
    }
    public void setToday(){
        calYear = today.get(Calendar.YEAR);
        calMonth = today.get(Calendar.MONTH);
        calDayOfMon = today.get(Calendar.DAY_OF_MONTH);								//���� �⵵, ��, ���� �����ͼ� today�� �־���
        makeCalData(today);																							
    }
    private void makeCalData(Calendar cal){	//Ķ����Ÿ��(��, ��, ��)�� cal�� �޾ƿ�  Ex)���ñ������� 2019�� 10�� 8�� �̶�� �ϸ�  DayOfWeek = 3(ȭ����) DayOfMonth = 8(8��), StartingPos = 2
        // 1���� ��ġ�� ������ ��¥�� ���� �ش� ((���� + 7) -�ش� ��%7)%7
        int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7; //���� ������
        if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);		// ������ ��¥ : �ش� ���� ������ ��¥ -> 2�����̸� ����üũ �� �����Ͻ� 29�Ϸ� Set
        else calLastDate = calLastDateOfMonth[calMonth];	//2������ �ƴϸ� day�� calLastDateOfMonth���� �����ͼ� �ٷ� ����
        // �޷� �迭 �ʱ�ȭ
        for(int i = 0 ; i<CAL_HEIGHT ; i++){
            for(int j = 0 ; j<CAL_WIDTH ; j++){
                calDates[i][j] = 0;
            }
        }
        // �޷� �迭�� �� ä���ֱ�
        for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){		//6�� ����
        	if(i == 0) k = calStartingPos;	//ó�� i�� ���� ������������ k�� �־���
            else k = 0;		//2���� ���ʹ� �Ͽ��Ϻ��� ����
            for(int j = k ; j<CAL_WIDTH ; j++){	//1�־� ���Ϻ� �迭�� �־���
                if(num <= calLastDate) calDates[i][j]=num++;
            }
        }
    }
    private int leapCheck(int year){ // �������� Ȯ���ϴ� �Լ�(�����Ͻ� 1�� Return)
        if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;		
        else return 0;
    }
    public void moveMonth(int mon){ // ����޷� ���� n�� ���ĸ� �޾� �޷� �迭�� ����� �Լ�(1���� +12, -12�޷� �̵� ����)
        calMonth += mon;			// ��ư Ŭ�������� calMonth�� �ٲ���(>�� +1, <�� -1, >>�� +12(1��) <<�� -12(1��)
        if(calMonth>11) while(calMonth>11){
            calYear++;
            calMonth -= 12;
        } else if (calMonth<0) while(calMonth<0){
            calYear--;
            calMonth += 12;
        }
        cal = new GregorianCalendar(calYear,calMonth,calDayOfMon); // Ķ������ ���� cal�� ���õ�(�ٲ� ��, ��, ���� Set) ������ �ٲ���
        makeCalData(cal);	//cal�迭 �ٽø���� makeCalData ����
    }
}

public class CalendarFrame extends CalendarDataManager{ // CalendarDataManager�� GUI + �޸��� + �ð�
    // â ������ҿ� ��ġ��
    JFrame mainFrame;
    URL imgURL = this.getClass().getResource("IUimage/memoIcon.png");
    ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));	//������(title��)
   
    ScheduleMgr mgr;
    JPanel calOpPanel;
    JButton todayBut;
    JLabel todayLab;
    JButton lYearBut;
    JButton lMonBut;
    JLabel curMMYYYYLab;
    JButton nMonBut;
    JButton nYearBut;
    ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();

    JPanel calPanel;
    JButton weekDaysName[];
    JButton dateButs[][] = new JButton[6][7];
    listenForDateButs lForDateButs = new listenForDateButs();
    JPanel infoPanel;
    JLabel infoClock;

    JLabel selectedDate;
    JTextArea memoArea;
    JScrollPane memoAreaSP;
    JPanel memoSubPanel;
    JButton saveBut;
    JButton delBut;
    JButton clearBut;

    JPanel frameBottomPanel;
    JLabel bottomInfo = new JLabel("Welcome to Memo Calendar!");
    //���, �޼���
    final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
    final String title = "Calendar v1.0";
    final String SaveButMsg1 = "�� MemoData������ �����Ͽ����ϴ�.";
    final String SaveButMsg2 = "�޸� ���� �ۼ��� �ּ���.";
    final String SaveButMsg3 = "<html><font color=red>ERROR : ���� ���� ����</html>";
    final String DelButMsg1 = "�޸� �����Ͽ����ϴ�.";
    final String DelButMsg2 = "�ۼ����� �ʾҰų� �̹� ������ memo�Դϴ�.";
    final String DelButMsg3 = "<html><font color=red>ERROR : ���� ���� ����</html>";
    final String ClrButMsg1 = "�Էµ� �޸� ������ϴ�.";

    private String userid = "";
    public CalendarFrame(String userid){ //������� ������ ���ĵǾ� ����. �� �ǳ� ���̿� ���ٷ� ����
    	this.userid = userid;
        mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700,400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setIconImage(icon.getImage());
        mainFrame.setUndecorated(true);
        
        calOpPanel = new JPanel();
        todayBut = new JButton("Today");
        todayBut.setToolTipText("Today");
        todayBut.addActionListener(lForCalOpButtons);
        todayLab = new JLabel(today.get(Calendar.MONTH)+1+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR));
        lYearBut = new JButton("<<");
        lYearBut.setToolTipText("Previous Year");
        lYearBut.addActionListener(lForCalOpButtons);
        lMonBut = new JButton("<");	
        lMonBut.setToolTipText("Previous Month");
        lMonBut.addActionListener(lForCalOpButtons);
        curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
        nMonBut = new JButton(">");
        nMonBut.setToolTipText("Next Month");
        nMonBut.addActionListener(lForCalOpButtons);
        nYearBut = new JButton(">>");
        nYearBut.setToolTipText("Next Year");
        nYearBut.addActionListener(lForCalOpButtons);
        
        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoClock = new JLabel("", SwingConstants.RIGHT);
        infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(infoClock, BorderLayout.NORTH);
        
        Dimension infoPanelSize=infoPanel.getPreferredSize();
        infoPanelSize.height = 65;
        infoPanel.setPreferredSize(infoPanelSize);
        JButton exitButton = new JButton("Ȩ");
        exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				new ScheduleMain(userid);
			}
		});
        
        calOpPanel.setLayout(new GridBagLayout());
        GridBagConstraints calOpGC = new GridBagConstraints();
        calOpGC.gridx = 1;
        calOpGC.gridy = 1;
        calOpGC.gridwidth = 2;
        calOpGC.gridheight = 1;
        calOpGC.weightx = 1;
        calOpGC.weighty = 1;
        calOpGC.insets = new Insets(5,5,0,0);
        calOpGC.anchor = GridBagConstraints.WEST;
        calOpGC.fill = GridBagConstraints.NONE;
        calOpPanel.add(todayBut,calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 2;
        calOpGC.gridy = 1;
        calOpPanel.add(exitButton, calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 6;
        calOpGC.gridy = 1;
        calOpPanel.add(infoPanel,calOpGC);
        calOpGC.anchor = GridBagConstraints.CENTER;
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 1;
        calOpGC.gridy = 2;
        calOpPanel.add(lYearBut,calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 2;
        calOpGC.gridy = 2;
        calOpPanel.add(lMonBut,calOpGC);
        calOpGC.gridwidth = 2;
        calOpGC.gridx = 3;
        calOpGC.gridy = 2;
        calOpPanel.add(curMMYYYYLab,calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 5;
        calOpGC.gridy = 2;
        calOpPanel.add(nMonBut,calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 6;
        calOpGC.gridy = 2;
        calOpPanel.add(nYearBut,calOpGC);

        calPanel=new JPanel();
        //���� ��ư ���ñ���
        weekDaysName = new JButton[7];
        for(int i=0 ; i<CAL_WIDTH ; i++){
            weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);			//�� �� ȭ �� �� �� �� ��
            weekDaysName[i].setBorderPainted(false);
            weekDaysName[i].setContentAreaFilled(false);
            weekDaysName[i].setForeground(Color.WHITE);
            if(i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50));
            else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));
            else weekDaysName[i].setBackground(new Color(150, 150, 150));
            weekDaysName[i].setOpaque(true);
            weekDaysName[i].setFocusPainted(false);
            calPanel.add(weekDaysName[i]);
        }
        //��¥ ��ư ����
        for(int i=0 ; i<CAL_HEIGHT ; i++){					//CAL_HEIGHT = 6
            for(int j=0 ; j<CAL_WIDTH ; j++){					// CAL_WIDTH = 7
                dateButs[i][j]=new JButton();
                dateButs[i][j].setBorderPainted(false);
                dateButs[i][j].setContentAreaFilled(false);
                dateButs[i][j].setBackground(Color.WHITE);
                dateButs[i][j].setOpaque(true);
                dateButs[i][j].addActionListener(lForDateButs);
                calPanel.add(dateButs[i][j]);
            }
        }
        calPanel.setLayout(new GridLayout(0,7,2,2));
        calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        showCal(); // �޷��� ǥ��

        selectedDate = new JLabel("<Html><font size=3>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</html>", SwingConstants.LEFT);
        selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

   
        memoArea = new JTextArea();
        memoArea.setLineWrap(true);
        memoArea.setWrapStyleWord(true);
        memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        readMemo();

        memoSubPanel=new JPanel();
        saveBut = new JButton("Save");
        saveBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    File f= new File("MemoData");
                    if(!f.isDirectory()) f.mkdir();

                    String memo = memoArea.getText();
                    if(memo.length()>0){
                    	File tempFil = new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt");
                    	FileWriter tempF = new FileWriter(tempFil);
                        BufferedWriter out = new BufferedWriter(tempF);
                        String str = memoArea.getText();
                        out.write(str);
                        out.close();
                        bottomInfo.setText(calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt"+SaveButMsg1);
                        System.out.println(tempFil.getAbsolutePath());
                    }
                    else
                        bottomInfo.setText(SaveButMsg2);
                } catch (IOException e) {
                    bottomInfo.setText(SaveButMsg3);
                }
                showCal();
            }
        });
        delBut = new JButton("Delete");
        delBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                memoArea.setText("");
                File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt");
                if(f.exists()){
                    f.delete();
                    showCal();
                    bottomInfo.setText(DelButMsg1);
                }
                else
                    bottomInfo.setText(DelButMsg2);
            }
        });
        clearBut = new JButton("Clear");
        clearBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                memoArea.setText(null);
                bottomInfo.setText(ClrButMsg1);
            }
        });
        memoSubPanel.add(saveBut);
        memoSubPanel.add(delBut);
        memoSubPanel.add(clearBut);

        //calOpPanel, calPanel��  frameSubPanelWest�� ��ġ
        JPanel frameSubPanelWest = new JPanel();
        Dimension calOpPanelSize = calOpPanel.getPreferredSize();
        calOpPanelSize.height = 90;
        calOpPanel.setPreferredSize(calOpPanelSize);
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
        frameSubPanelWest.add(calPanel,BorderLayout.CENTER);

        //infoPanel, memoPanel��  frameSubPanelEast�� ��ġ
//        JPanel frameSubPanelEast = new JPanel();
//        frameSubPanelEast.setLayout(new BorderLayout());
//        frameSubPanelEast.add(infoPanel,BorderLayout.SOUTH);
        //�޸��ǳ� Add

        Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
        frameSubPanelWestSize.width = 700; //700
        frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

        //�ڴʰ� �߰��� bottom Panel..
        frameBottomPanel = new JPanel();
        frameBottomPanel.add(bottomInfo);

        //frame�� ���� ��ġ
        mainFrame.setLayout(new BorderLayout());
//        mainFrame.add(frameSubPanelEast, BorderLayout.NORTH);
        mainFrame.add(frameSubPanelWest, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        focusToday(); //���� ��¥�� focus�� �� (mainFrame.setVisible(true) ���Ŀ� ��ġ�ؾ���)

        //Thread �۵�(�ð�, bottomMsg �����ð��� ����)
        ThreadConrol threadCnl = new ThreadConrol();
        threadCnl.start();
    }
    private void focusToday(){
        if(today.get(Calendar.DAY_OF_WEEK) == 1)
            dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
        else
            dateButs[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
    }
    private void readMemo(){
        try{
            File f = new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt");
            if(f.exists()){
                BufferedReader in = new BufferedReader(new FileReader("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDayOfMon<10?"0":"")+calDayOfMon+".txt"));
                String memoAreaText= new String();
                while(true){
                    String tempStr = in.readLine();
                    if(tempStr == null) break;
                    memoAreaText = memoAreaText + tempStr + System.getProperty("line.separator");
                }
                memoArea.setText(memoAreaText);
                in.close();
            }
            else memoArea.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showCal(){
        mgr = new ScheduleMgr();
        String monthString = "";
        int monthCheck = calMonth+1;
        System.out.println(monthCheck);
        if(monthCheck<10) 
        	monthString = "0" +Integer.toString(monthCheck); 
        else
        	monthString = Integer.toString(monthCheck);
     
        String monthFirst = Integer.toString(calYear)+monthString+"01";//��Ʈ����
        String monthSecond = Integer.toString(calYear)+monthString+"32";
        System.out.println(monthFirst);
        System.out.println(monthSecond);
        ArrayList<String> dateArray = new ArrayList<String>();
        Vector<Integer> dateArrayInt;
       Vector<ScheduleBean> loadSchedule =  mgr.printMonth(userid, monthFirst, monthSecond);
       //���⿡�� ������ ���� ����Ʈ�� �̾ƿԾ�
       for(int i=0; i<loadSchedule.size();i++) {
    	   ScheduleBean bean = loadSchedule.get(i);
    	  Vector<Object> ad = new Vector<Object>();
    	  ad.add(bean.getpidx());
    	  ad.add(bean.getptitle());
    	  ad.add(bean.getpdate());
    	  ad.add(bean.getpdetail());
    	  dateArray.add(bean.getpdate());
       }
       System.out.println(dateArray.size());
       int planYear = 0, planMonth=0, planDay=0;
       dateArrayInt =  new Vector<Integer>();
       if(dateArray.size()!=0) {
    	   for(int e = 0; e<dateArray.size();e++) {
    		   dateArrayInt.add(Integer.parseInt(dateArray.get(e).substring(0,4)));
    		   dateArrayInt.add(Integer.parseInt(dateArray.get(e).substring(4,6)));
    		   dateArrayInt.add(Integer.parseInt(dateArray.get(e).substring(6,8)));
    	   }
    	   System.out.println(dateArrayInt.get(2));
    	   if(dateArrayInt.size()>3)
    		   System.out.println("�迭�� 2���̻��Դϴ�"+dateArrayInt.get(5));
    	   if(dateArrayInt.size()>6)
    		   System.out.println("�迭�� 3�� �̻��Դϴ�");
	       //���Ϳ� �÷� 1 �� 3���� ��
	       //���⼭ �� �� �� �̾���
       }

        for(int i=0;i<CAL_HEIGHT;i++){
            for(int j=0;j<CAL_WIDTH;j++){
                String fontColor="black";
                if(j==0) fontColor="red";
                else if(j==6) fontColor="blue";

                File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
                if(f.exists()){
                    dateButs[i][j].setText("<html><b><font color="+fontColor+">"+calDates[i][j]+"</font></b></html>");
                }
                else dateButs[i][j].setText("<html><font color="+fontColor+">"+calDates[i][j]+"</font></html>");

                JLabel todayMark = new JLabel("<html><font color=green>*</html>");
                JLabel planMark = new JLabel("<html><font color=red>*</html>");
                //�׽���
          
                //���⼭ ��ȹ �߰��ϰ��� �Ű��ָ� ����
                //�׽�������Ʈ #1(��ȹ �߰�)
               // �����ŭ if���� �ٽ� ���������.
                
                dateButs[i][j].removeAll();
                if(calMonth == today.get(Calendar.MONTH) &&
                        calYear == today.get(Calendar.YEAR) &&
                        calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
                    dateButs[i][j].add(todayMark);
                    dateButs[i][j].setToolTipText("Today");
                }
                //���� �κ�(size��ŭ �߰��ؾ���) 0 1 2�̾���
                
                for(int w =0; w<(dateArrayInt.size()+1)/3;w++) {
                	 System.out.println("w"+w);
                	 int q = w*3;
                     if(calYear == dateArrayInt.get(q) && calMonth ==(dateArrayInt.get(q+1))-1 && calDates[i][j] == dateArrayInt.get((q+2)))
                    {
                    	dateButs[i][j].add(planMark);
                    	dateButs[i][j].setToolTipText("Plan");
                    }
                }
                
                if(calDates[i][j] == 0) dateButs[i][j].setVisible(false);
                else dateButs[i][j].setVisible(true);
            }
        }
    }
    private class ListenForCalOpButtons implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == todayBut){				//�� ������ ��� �޷� �迭 �ʱ�ȭ �� �� �迭�� �ٽ� �־��ش�
                setToday();
                lForDateButs.actionPerformed(e);
                focusToday();										//���ñ������� �޷� focus
            }
            else if(e.getSource() == lYearBut) moveMonth(-12);
            else if(e.getSource() == lMonBut) moveMonth(-1);
            else if(e.getSource() == nMonBut) moveMonth(1);
            else if(e.getSource() == nYearBut) moveMonth(12);
            
            curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
            showCal();
        }
    }
    private class listenForDateButs implements ActionListener{		//���° �迭�� Ŭ���ߴ��� �������0,0���� ����
        public void actionPerformed(ActionEvent e) {
            int k=0,l=0;
            for(int i=0 ; i<CAL_HEIGHT ; i++){
                for(int j=0 ; j<CAL_WIDTH ; j++){
                    if(e.getSource() == dateButs[i][j]){
                        k=i;
                        l=j;
      
                    }
                }
            }

            if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today��ư�� ���������� �� actionPerformed�Լ��� ����Ǳ� ������ ���� �κ�

            cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);

            String dDayString = new String();
            int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
            if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR))
                    && (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                    && (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today";
            else if(dDay >=0) dDayString = "D-"+(dDay+1);
            else if(dDay < 0) dDayString = "D+"+(dDay)*(-1);

            selectedDate.setText("<Html><font size=3>"+(calMonth+1)+"/"+calDayOfMon+"/"+calYear+"&nbsp;("+dDayString+")</html>");
            System.out.println(cal.get(Calendar.YEAR));
            System.out.println(cal.get(Calendar.MONTH)+1);
            System.out.println(cal.get(Calendar.DAY_OF_MONTH));		//Ŭ���� ��, ��, �� ���
            String overYear = Integer.toString(cal.get(Calendar.YEAR));
            String overMonth ="";
            String overDay = "";
            
            if(cal.get(Calendar.MONTH)+1<10)
            	overMonth = "0" +Integer.toString(cal.get(Calendar.MONTH)+1);
            else
            	overMonth = Integer.toString(cal.get(Calendar.MONTH)+1);
            if(cal.get(Calendar.DAY_OF_MONTH)<10)
            	overDay = "0" +Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            else
            	overDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            
            String selectedDate = overYear + overMonth + overDay;
            if(!(todayBut==e.getSource()))
            	new calendarPlanlistView(userid, selectedDate);
            
        }
    }
    private class ThreadConrol extends Thread{
        public void run(){
            boolean msgCntFlag = false;
            int num = 0;
            String curStr = new String();
            while(true){
                try{
                    today = Calendar.getInstance();
                    String amPm = (today.get(Calendar.AM_PM)==0?"AM":"PM");
                    String hour;
                    if(today.get(Calendar.HOUR) == 0) hour = "12";
                    else if(today.get(Calendar.HOUR) == 12) hour = " 0";
                    else hour = (today.get(Calendar.HOUR)<10?" ":"")+today.get(Calendar.HOUR);
                    String min = (today.get(Calendar.MINUTE)<10?"0":"")+today.get(Calendar.MINUTE);
                    String sec = (today.get(Calendar.SECOND)<10?"0":"")+today.get(Calendar.SECOND);
                    infoClock.setText(amPm+" "+hour+":"+min+":"+sec);

                    sleep(1000);
                    String infoStr = bottomInfo.getText();

                    if(infoStr != " " && (msgCntFlag == false || curStr != infoStr)){
                        num = 5;
                        msgCntFlag = true;
                        curStr = infoStr;
                    }
                    else if(infoStr != " " && msgCntFlag == true){
                        if(num > 0) num--;
                        else{
                            msgCntFlag = false;
                            bottomInfo.setText(" ");
                        }
                    }
                }
                catch(InterruptedException e){
                    System.out.println("Thread:Error");
                }
            }
        }
    }
}