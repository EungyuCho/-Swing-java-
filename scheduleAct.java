package team_p1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class scheduleAct extends JFrame implements ActionListener {
	JFrame frame;
	JButton add_btn, del_btn, upd_btn, sch_btn, home_btn, main_btn;
	JTable table, jTable;
	JLabel label,l1,l2,l3,l4,l5, ls;
	List list;
	JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8;
	JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, psm, ps1, ps2, ps3, ps4;
	JButton save_btn, getback_btn, usave_btn, search_btn;	
	Vector<ScheduleBean> vtable;	
	ScheduleMgr mgr;
	JScrollPane sp;	
	int idx;
	private int count =0;
	int updateIdx;
	boolean selectCheck = true;
	private int deleteIndex =0;
	private boolean continueInput = false;
	private DefaultTableModel model = null;
	private Vector data, title = null;
	private String userid = "";
	Font titleFont = new Font("바탕",Font.BOLD,30);
	Font btnFont = new Font("바탕",Font.BOLD,15);
	Font cateFont = new Font("바탕",Font.BOLD,20);
	
	
	public scheduleAct(String userid) {
		this.userid = userid;
		setSize(500,350);
		setTitle("SCHEDULE");
		setUndecorated(true);
		setLocationRelativeTo(null);
		
		mgr=new ScheduleMgr();
		p1 = new JPanel();
		p2 = new JPanel();		
		label = new JLabel("PLAN YOUR ORDINARY LIFE");
		label.setFont(titleFont);
		add(label, BorderLayout.PAGE_START);
		
		
		add_btn = new JButton("추가");
		del_btn = new JButton("삭제");
		upd_btn = new JButton("수정");
		home_btn = new JButton("테이블보기");
		sch_btn = new JButton("검색");
		main_btn = new JButton("홈");
		add_btn.addActionListener(this);
		del_btn.addActionListener(this);
		upd_btn.addActionListener(this);
		home_btn.addActionListener(this);
		sch_btn.addActionListener(this);
		main_btn.addActionListener(this);
		
		p1=new JPanel();
		p1.add(add_btn); p1.add(del_btn); p1.add(upd_btn); 
		p1.add(sch_btn); p1.add(home_btn); p1.add(main_btn); 		
		tf8 = new JTextField(15);
		p1.add(tf8);
		add(p1, BorderLayout.PAGE_END);	
		
		
		viewTable(userid);
		setVisible(true);	
}
	public void viewTable(String userid) {
		data = new Vector<>();
		title = new Vector<>();
		vtable = mgr.listPlan(userid);

		title.add("번호");	title.add("타이틀");	title.add("날짜");
		title.add("내용");	title.add("#해시태그"); title.add("작성시간");
		
		DefaultTableModel model = new DefaultTableModel(title,0);
		for(int i=0; i<vtable.size();i++) {
			ScheduleBean bean = vtable.get(i);
			Vector <Object>tt = new Vector<Object>();
			tt.add(bean.getpidx());
			tt.add(bean.getptitle());
			tt.add(bean.getpdate());
			tt.add(bean.getpdetail());
			tt.add(bean.getphashTag());
			tt.add(bean.getcdate());
			model.addRow(tt);
			
		}
		//테이블 커스터마이징1
		table = new JTable(model);
		table.setPreferredSize(new Dimension(300,300));
		table.setPreferredScrollableViewportSize(new Dimension(500,80));
		table.getTableHeader().setFont(new Font("바탕",Font.BOLD,17));
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(32,136,203));
		table.getTableHeader().setForeground(new Color(255,255,255));
		table.setRowHeight(25);
		JTableHeader Theader = table.getTableHeader(); 
		((DefaultTableCellRenderer)Theader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		TableColumn col0 =table.getColumnModel().getColumn(0);
		TableColumn col1 =table.getColumnModel().getColumn(1);
		TableColumn col2 =table.getColumnModel().getColumn(2);
		TableColumn col3 =table.getColumnModel().getColumn(3);
		TableColumn col4 =table.getColumnModel().getColumn(4);
		TableColumn col5 =table.getColumnModel().getColumn(5);
		//table.setShowGrid(false); //셀내에 있는 줄 없애기
		//table.setShowHorizontalLines(false); //가로 줄 
		table.setShowVerticalLines(false); //세로 줄
		table.setGridColor(new Color(222,222,222)); //줄 색깔
		//table.setBackground(Color.pink);
		col0.setPreferredWidth(2);	col1.setPreferredWidth(80);	col2.setPreferredWidth(35);
		col3.setPreferredWidth(150); col4.setPreferredWidth(80); col5.setPreferredWidth(50);
		table.setFont(new Font("바탕",Font.PLAIN,12));
		sp = new JScrollPane(table);		
		add(sp);	
		
		 jTable = new JTable(model);
			

			TableRowSorter<TableModel> rowSorter
		    = new TableRowSorter<>(jTable.getModel());
			
			tf8.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					String text = tf8.getText();
					if (text.trim().length()==0 ) {
						rowSorter.setRowFilter(null);
					}else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?!)"+text));}
				}
				@Override	public void removeUpdate(DocumentEvent e) {	
					 String text = tf8.getText();
		             if (text.trim().length() == 0) {
		                    rowSorter.setRowFilter(null);
		                } else {rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));}
				}
				@Override   public void changedUpdate(DocumentEvent e) {
					throw new UnsupportedOperationException("Not supported yet.");
				}				
			});
		
	}
	//추가
	public void insertpage() {
		label.setText("add your PLAN");
		//label=new JLabel();
		//label.setText("일정추가");
		//label.setBackground(Color.gray);
		p2.setLayout(new GridLayout(5,1));
		continueInput = false;
		p3 = new JPanel();
		l1=new JLabel("타이틀:  ");
		l1.setFont(cateFont);
		p3.add(l1);
		tf1 = new JTextField(20);
		tf1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {					
					tf2.requestFocus();
				}
			}
		});
		p3.add(tf1);
		p2.add(p3);
		
		p4 = new JPanel();
		l2=new JLabel("날짜:  ");
		l2.setFont(cateFont);
		p4.add(l2);
		
		tf2 = new JTextField(20);		
		p4.add(tf2);
		p2.add(p4);				
		tf2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {				
				if(e.getKeyCode()==10) {tf3.requestFocus();}
			}
		});
		p5 = new JPanel();
		l3=new JLabel("세부내용:  ");
		l3.setFont(cateFont);
		p5.add(l3);
		tf3 = new JTextField(20);
		p5.add(tf3);
		p2.add(p5);
		tf3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{if(e.getKeyCode()==10) {tf4.requestFocus();}
			}
		});
		p6 = new JPanel();
		l4=new JLabel("#해시태그  :");
		l4.setFont(cateFont);
		p6.add(l4);		
		tf4 = new JTextField(20);
		
		p6.add(tf4);
		p2.add(p6);
		tf4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10) {
					ScheduleBean bean = new ScheduleBean();
					System.out.println("입력시작");
					bean.setpidx(idx);
					bean.setptitle(tf1.getText());
					bean.setpdate(tf2.getText());
					bean.setpdetail(tf3.getText());
					bean.setphashTag(tf4.getText());
					bean.setpuser(userid);
					mgr.insertPlan(userid, bean);
					if(continueInput==true) {
						tf1.setText("");
						tf2.setText("");		
						tf3.setText("");
						tf4.setText("");
						tf1.requestFocus();
					}else {
						p2.removeAll();
						remove(p2);
						table.removeAll();
						remove(table);
						vtable.removeAllElements();
						setDeleteIdx(1);
						viewTable(userid);						
						repaint(); validate(); invalidate();
						add_btn.setEnabled(true);
						del_btn.setEnabled(true);
						upd_btn.setEnabled(true);
						home_btn.setEnabled(true);
						selectCheck = true;
					}					
				}
			}
		});
		save_btn = new JButton("저장");
		save_btn.addActionListener(this);
		p7 = new JPanel();
		p7.add(save_btn);
		p2.add(p7);
		
		add(p2, BorderLayout.CENTER);		
	}
	
	//수정
	public void updatePage(ScheduleBean bean) {		
		label.setText("수정페이지");
		p2.setLayout(new GridLayout(5,1));
		idx = bean.getpidx();
		
		p3 = new JPanel();		
		l1=new JLabel("타이틀  :");
		l1.setFont(cateFont);
		p3.add(l1);
		tf1 = new JTextField(bean.getptitle(), 20);
		p3.add(tf1);
		p2.add(p3);
		
		p4 = new JPanel();
		l2=new JLabel("날짜  :");
		l2.setFont(cateFont);		
		p4.add(l2);
		tf2 = new JTextField(bean.getpdate(), 20);		
		p4.add(tf2);
		p2.add(p4);		
		p5 = new JPanel();
		l3=new JLabel("세부내용  :");
		l3.setFont(cateFont);
		p5.add(l3);
		tf3 = new JTextField(bean.getpdetail(), 20);
		p5.add(tf3);
		p2.add(p5);
		
		p6 = new JPanel();
		l4=new JLabel("#해시태그  :");		
		l4.setFont(cateFont);
		p6.add(l4);
		tf4 = new JTextField(bean.getphashTag(), 20);
		p6.add(tf4);
		p2.add(p6);
		
		p7 = new JPanel();
		usave_btn = new JButton("저장");
		usave_btn.addActionListener(this);
		p7.add(usave_btn);
		p2.add(p7);
		
		add(p2, BorderLayout.CENTER);		
	}
	public void searchPage() {
		label.setText("search your #PLAN");				
		p2=new JPanel();		
		l1=new JLabel("#해시태그 검색어  :");
		l1.setFont(btnFont);
		p2.add(l1);
		tf7=new JTextField(20);
		p2.add(tf7);
		search_btn=new JButton("search");
		count = 0;
		System.out.println(count);
		search_btn.addActionListener(this);
		p2.add(search_btn);			
		 
			tf7.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					
					if(e.getKeyCode()==10) {			//엔터		
						if(count==0)
							talbeUpdateInSearch();
						else
							p2.remove(table);
							p2.remove(sp);							
							repaint();
							talbeUpdateInSearch();
					}					
				}
			});				
		add(p2, BorderLayout.CENTER);
	}
private void talbeUpdateInSearch() {
	count++;
	System.out.println(count);
	ScheduleBean bean = new ScheduleBean();
	System.out.println("검색시작");
	String a =tf7.getText();
	mgr.searchPlan(userid, a);
	
	data = new Vector<>();
	title = new Vector<>();
	vtable = mgr.searchPlan(userid, a);

	title.add("번호");	title.add("타이틀");	title.add("날짜");
	title.add("내용");	title.add("#해시태그"); title.add("작성시간");
	
	DefaultTableModel model = new DefaultTableModel(title,0);
	for(int i=0; i<vtable.size();i++) {
		bean = vtable.get(i);
		Vector <Object>tt = new Vector<Object>();
		tt.add(bean.getpidx());
		tt.add(bean.getptitle());
		tt.add(bean.getpdate());
		tt.add(bean.getpdetail());
		tt.add(bean.getphashTag());
		tt.add(bean.getcdate());
		model.addRow(tt);
	}
	//테이블 커스터마이징2
	table = new JTable(model);
	sp = new JScrollPane(table);
	table.setPreferredSize(new Dimension(300,300));
	table.setPreferredScrollableViewportSize(new Dimension(500,80));
	table.getTableHeader().setFont(new Font("바탕",Font.BOLD,17));
	table.getTableHeader().setOpaque(false);
	table.getTableHeader().setBackground(new Color(32,136,203));
	table.getTableHeader().setForeground(new Color(255,255,255));
	table.setRowHeight(25);
	table.setShowVerticalLines(false); //세로 줄
	table.setGridColor(new Color(222,222,222)); //줄 색깔
	
	
	table.setFont(new Font("바탕",Font.PLAIN,12));
	sp.setBorder(BorderFactory.createEmptyBorder());
	setLayout(null);
	System.out.println(getLayout().toString());
	//테이블 변경부분
	p2.add(sp);				
	repaint();	
	setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e) {	
	Object obj = e.getSource();
		// 리무브
	if(obj==add_btn) {
		new scheduleAdd_2(userid);
		dispose();
		p2.removeAll();
		remove(p2);	remove(table);	remove(sp);
		table.removeAll();
		insertpage();
		repaint();	validate();	invalidate();
		tf1.requestFocus();
		add_btn.setEnabled(false);
		del_btn.setEnabled(false);
		upd_btn.setEnabled(false);
		repaint();	
		
	}else if(obj==del_btn) {
		int i =vtable.get(table.getSelectedRow()).getpidx();
		setDeleteIdx(table.getSelectedRow());
		showMessage("삭제중");
		mgr.deletePlan(i);		
		//System.out.println("i는"+i);
		//System.out.println("인덱스는"+deleteIndex);
		p2.removeAll();	remove(p2);
		table.removeAll();	remove(table);	remove(sp);
		vtable.removeAllElements();
		
		mgr.resetPidx();
		showMessage("삭제되었습니다");
		viewTable(userid);
		repaint(); validate(); invalidate();
	}else if(obj==upd_btn) {
		try {if(selectCheck==true) {
			updateIdx = vtable.get(table.getSelectedRow()).getpidx();		
			ScheduleBean bean = vtable.get(table.getSelectedRow());
			table.removeAll(); remove(table); remove(sp);
			updatePage(bean);
			repaint(); validate(); invalidate();
			del_btn.setEnabled(false);
			upd_btn.setEnabled(false);					
		}
			
		} catch (Exception e2) {
			showMessage("항목을 선택해주세요");
			e2.printStackTrace();
			}
	}else if (obj==sch_btn) {
		//label.setText("search your #PLAN");
		p2.removeAll();
		remove(p2);	remove(table);	remove(sp);
		table.removeAll();
		searchPage();
		repaint(); validate(); invalidate();
		add_btn.setEnabled(false);
		del_btn.setEnabled(false);
		upd_btn.setEnabled(false);
		sch_btn.setEnabled(true);		
	}else if(obj==home_btn) {
		label.setText("PLAN YOUR ORDINARY LIFE");
		remove(p2);	p2.removeAll();		
		remove(table); table.removeAll(); remove(sp);		
		vtable.removeAllElements();		
		setDeleteIdx(1);			
		viewTable(userid);		
		repaint(); 	validate();	invalidate();
		add_btn.setEnabled(true);
		del_btn.setEnabled(true);
		upd_btn.setEnabled(true);
		sch_btn.setEnabled(true);
		selectCheck = true;	
		//setVisible(true);			
		//new scheduleAct();
		//dispose();
	}else if(obj==save_btn) {		
		ScheduleBean bean = new ScheduleBean();
		if(tf1.getText().equals(tf1.getText().trim())) {
			System.out.println("입력시작");
		bean.setpidx(idx);
		bean.setptitle(tf1.getText());
		bean.setpdate(tf2.getText());
		bean.setpdetail(tf3.getText());
		bean.setphashTag(tf4.getText());
		bean.setpuser(userid);
		mgr.insertPlan(userid, bean);
		if(continueInput==true) {
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			tf1.requestFocus();
		}else {
			p2.removeAll();	remove(p2);	
			table.removeAll(); remove(table);
			vtable.removeAllElements();
			setDeleteIdx(1);
			viewTable(userid);
			//label.setText("PLAN YOUR ORDINARY LIFE");
			repaint(); validate(); invalidate();
			add_btn.setEnabled(true);
			del_btn.setEnabled(true);
			upd_btn.setEnabled(true);
			home_btn.setEnabled(true);
			selectCheck = true;
		}					
		}
	}else if(obj==search_btn) {	
		if(count==0)
			talbeUpdateInSearch();
		else
			//p2.remove(table);
			//p2.remove(sp);							
			repaint();
			talbeUpdateInSearch();	
	}else if(obj==main_btn) {new ScheduleMain(userid); dispose();
	}else if(obj==usave_btn) {
		ScheduleBean bean = new ScheduleBean();
		bean.setpidx(updateIdx);
		bean.setptitle(tf1.getText());
		bean.setpdate(tf2.getText());
		bean.setpdetail(tf3.getText());
		bean.setphashTag(tf4.getText());
		if(mgr.updatePlan(bean));{
		p2.removeAll();
		remove(p2);
		table.removeAll();
		remove(table);
		viewTable(userid);
		add_btn.setEnabled(true);
		upd_btn.setEnabled(true);
		del_btn.setEnabled(true);
		sch_btn.setEnabled(true);
		}		
	} validate();
	}
public void showMessage(String message) {
	JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);		
}

public int getDeleteIdx() {
	return deleteIndex;
}
public void setDeleteIdx(int a) {
	this.deleteIndex = a+1;
	if(a==0) {
		this.deleteIndex=0;
	}
}
}

