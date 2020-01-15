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

class CalendarDataManager{ // 6*7배열에 나타낼 달력 값을 구하는 class
    static final int CAL_WIDTH = 7;																				//배열 행 갯수(월화수목금토일)
    final static int CAL_HEIGHT = 6;																			//배열 열 갯수(6주)
    int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];									//몇일 int
    int calYear;																													//몇년 int
    int calMonth;																												//몇달	int
    int calDayOfMon;																										//
    final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};			//달의 마지막 int 배열
    int calLastDate;
    Calendar today = Calendar.getInstance();																//Calendar형 today 선언(Calendar 형태 가져옴) ->  Today 저장용				
    Calendar cal;
    public CalendarDataManager(){
        setToday();																				
    }
    public void setToday(){
        calYear = today.get(Calendar.YEAR);
        calMonth = today.get(Calendar.MONTH);
        calDayOfMon = today.get(Calendar.DAY_OF_MONTH);								//현재 년도, 달, 일자 가져와서 today에 넣어줌
        makeCalData(today);																							
    }
    private void makeCalData(Calendar cal){	//캘린더타입(년, 월, 일)형 cal로 받아옴  Ex)오늘기준으로 2019년 10월 8일 이라고 하면  DayOfWeek = 3(화요일) DayOfMonth = 8(8일), StartingPos = 2
        // 1일의 위치와 마지막 날짜를 구함 해당 ((요일 + 7) -해당 일%7)%7
        int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7; //시작 포지션
        if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);		// 마지막 날짜 : 해당 달의 마지막 일짜 -> 2월달이면 윤년체크 후 윤년일시 29일로 Set
        else calLastDate = calLastDateOfMonth[calMonth];	//2월달이 아니면 day를 calLastDateOfMonth에서 가져와서 바로 세팅
        // 달력 배열 초기화
        for(int i = 0 ; i<CAL_HEIGHT ; i++){
            for(int j = 0 ; j<CAL_WIDTH ; j++){
                calDates[i][j] = 0;
            }
        }
        // 달력 배열에 값 채워넣기
        for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){		//6주 돌림
        	if(i == 0) k = calStartingPos;	//처음 i가 돌때 시작포지션을 k에 넣어줌
            else k = 0;		//2주차 부터는 일요일부터 시작
            for(int j = k ; j<CAL_WIDTH ; j++){	//1주씩 요일별 배열을 넣어줌
                if(num <= calLastDate) calDates[i][j]=num++;
            }
        }
    }
    private int leapCheck(int year){ // 윤년인지 확인하는 함수(윤년일시 1을 Return)
        if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;		
        else return 0;
    }
    public void moveMonth(int mon){ // 현재달로 부터 n달 전후를 받아 달력 배열을 만드는 함수(1년은 +12, -12달로 이동 가능)
        calMonth += mon;			// 버튼 클릭에따라 calMonth를 바꿔줌(>는 +1, <는 -1, >>는 +12(1년) <<는 -12(1년)
        if(calMonth>11) while(calMonth>11){
            calYear++;
            calMonth -= 12;
        } else if (calMonth<0) while(calMonth<0){
            calYear--;
            calMonth += 12;
        }
        cal = new GregorianCalendar(calYear,calMonth,calDayOfMon); // 캘린더형 변수 cal에 세팅된(바뀐 달, 월, 요일 Set) 값으로 바꿔줌
        makeCalData(cal);	//cal배열 다시만드느 makeCalData 실행
    }
}

public class CalendarFrame extends CalendarDataManager{ // CalendarDataManager의 GUI + 메모기능 + 시계
    // 창 구성요소와 배치도
    JFrame mainFrame;
    URL imgURL = this.getClass().getResource("IUimage/memoIcon.png");
    ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));	//아이콘(title용)
   
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
    //상수, 메세지
    final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
    final String title = "Calendar v1.0";
    final String SaveButMsg1 = "를 MemoData폴더에 저장하였습니다.";
    final String SaveButMsg2 = "메모를 먼저 작성해 주세요.";
    final String SaveButMsg3 = "<html><font color=red>ERROR : 파일 쓰기 실패</html>";
    final String DelButMsg1 = "메모를 삭제하였습니다.";
    final String DelButMsg2 = "작성되지 않았거나 이미 삭제된 memo입니다.";
    final String DelButMsg3 = "<html><font color=red>ERROR : 파일 삭제 실패</html>";
    final String ClrButMsg1 = "입력된 메모를 비웠습니다.";

    private String userid = "";
    public CalendarFrame(String userid){ //구성요소 순으로 정렬되어 있음. 각 판넬 사이에 빈줄로 구별
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
        JButton exitButton = new JButton("홈");
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
        //요일 버튼 세팅구간
        weekDaysName = new JButton[7];
        for(int i=0 ; i<CAL_WIDTH ; i++){
            weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);			//일 월 화 수 목 금 토 일
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
        //날짜 버튼 생성
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
        showCal(); // 달력을 표시

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

        //calOpPanel, calPanel을  frameSubPanelWest에 배치
        JPanel frameSubPanelWest = new JPanel();
        Dimension calOpPanelSize = calOpPanel.getPreferredSize();
        calOpPanelSize.height = 90;
        calOpPanel.setPreferredSize(calOpPanelSize);
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
        frameSubPanelWest.add(calPanel,BorderLayout.CENTER);

        //infoPanel, memoPanel을  frameSubPanelEast에 배치
//        JPanel frameSubPanelEast = new JPanel();
//        frameSubPanelEast.setLayout(new BorderLayout());
//        frameSubPanelEast.add(infoPanel,BorderLayout.SOUTH);
        //메모판넬 Add

        Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
        frameSubPanelWestSize.width = 700; //700
        frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

        //뒤늦게 추가된 bottom Panel..
        frameBottomPanel = new JPanel();
        frameBottomPanel.add(bottomInfo);

        //frame에 전부 배치
        mainFrame.setLayout(new BorderLayout());
//        mainFrame.add(frameSubPanelEast, BorderLayout.NORTH);
        mainFrame.add(frameSubPanelWest, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        focusToday(); //현재 날짜에 focus를 줌 (mainFrame.setVisible(true) 이후에 배치해야함)

        //Thread 작동(시계, bottomMsg 일정시간후 삭제)
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
     
        String monthFirst = Integer.toString(calYear)+monthString+"01";//라스트데이
        String monthSecond = Integer.toString(calYear)+monthString+"32";
        System.out.println(monthFirst);
        System.out.println(monthSecond);
        ArrayList<String> dateArray = new ArrayList<String>();
        Vector<Integer> dateArrayInt;
       Vector<ScheduleBean> loadSchedule =  mgr.printMonth(userid, monthFirst, monthSecond);
       //여기에서 유저에 대한 리스트를 뽑아왔어
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
    		   System.out.println("배열이 2개이상입니다"+dateArrayInt.get(5));
    	   if(dateArrayInt.size()>6)
    		   System.out.println("배열이 3개 이상입니다");
	       //벡터에 플랜 1 당 3개씩 들어감
	       //여기서 년 월 일 뽑았음
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
                //테스팅
          
                //여기서 계획 추가하고나서 옮겨주면 가능
                //테스팅포인트 #1(계획 추가)
               // 사이즈만큼 if문을 다시 돌려줘야함.
                
                dateButs[i][j].removeAll();
                if(calMonth == today.get(Calendar.MONTH) &&
                        calYear == today.get(Calendar.YEAR) &&
                        calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
                    dateButs[i][j].add(todayMark);
                    dateButs[i][j].setToolTipText("Today");
                }
                //조건 부분(size만큼 추가해야함) 0 1 2뽑았음
                
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
            if(e.getSource() == todayBut){				//셋 투데이 경우 달력 배열 초기화 한 후 배열에 다시 넣어준다
                setToday();
                lForDateButs.actionPerformed(e);
                focusToday();										//오늘기준으로 달력 focus
            }
            else if(e.getSource() == lYearBut) moveMonth(-12);
            else if(e.getSource() == lMonBut) moveMonth(-1);
            else if(e.getSource() == nMonBut) moveMonth(1);
            else if(e.getSource() == nYearBut) moveMonth(12);
            
            curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
            showCal();
        }
    }
    private class listenForDateButs implements ActionListener{		//몇번째 배열을 클릭했는지 출력해줌0,0부터 시작
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

            if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today버튼을 눌렀을때도 이 actionPerformed함수가 실행되기 때문에 넣은 부분

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
            System.out.println(cal.get(Calendar.DAY_OF_MONTH));		//클릭한 년, 월, 달 출력
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