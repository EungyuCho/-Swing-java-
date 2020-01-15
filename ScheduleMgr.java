package team_p1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;


public class ScheduleMgr {
	scheduleAct Act;
	DBConnectionMgr pool;
	public ScheduleMgr() {
		pool = DBConnectionMgr.getInstance();	
	}
	
	//List
	public Vector<ScheduleBean> listPlan(String userid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector <ScheduleBean> vtable = new Vector<ScheduleBean>();
		System.out.println(userid);
		try {
			con = pool.getConnection();
			sql = "select * from planpro where puser = '" + userid + "'";
			pstmt = con.prepareStatement(sql);
			System.out.println("여기까지들어옴");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ScheduleBean bean = new ScheduleBean();
				bean.setpidx(rs.getInt(1));
				bean.setptitle(rs.getString(2));
				bean.setpdate(rs.getString(3));
				bean.setpdetail(rs.getString(4));
				bean.setphashTag(rs.getString(5));
				bean.setcdate(rs.getString(6));
				vtable.addElement(bean);
			}
		} catch (Exception e) {
			System.out.println("getting list error");
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}return vtable;
	}
		
//read
		public ScheduleBean getPlan(int pidx) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			ScheduleBean bean = new ScheduleBean();			
			
			try {
				con = pool.getConnection();
				sql = "select * from planpro where pidx=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, pidx);
				rs = pstmt.executeQuery();
				if(rs.next()) {					
					bean.setpidx(rs.getInt(1));
					bean.setptitle(rs.getString(2));
					bean.setpdate(rs.getString(3));
					bean.setpdetail(rs.getString(4));
					bean.setphashTag(rs.getString(5));
					bean.setcdate(rs.getString(6));
				}			
		  }catch(Exception e) {
			  e.printStackTrace();
		  }finally {
			  pool.freeConnection(con, pstmt, rs);
		  }return bean;	
		}
		//insert
		
	public boolean insertPlan(String userid, ScheduleBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "insert into planpro(ptitle, pdate, pdetail, phashTag, cdate, puser) values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getptitle());
			pstmt.setString(2, bean.getpdate());
			pstmt.setString(3, bean.getpdetail());
			pstmt.setString(4, bean.getphashTag());
			pstmt.setString(5, bean.getcdate());
			pstmt.setString(6, userid);
			int cnt = pstmt.executeUpdate();
			if(cnt == 1)
				flag = true;
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con,pstmt);
		}return flag;
	}
		//update
	public boolean updatePlan(ScheduleBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql="update planpro set ptitle=?, pdate=?, pdetail=?, phashTag=? where pidx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getptitle());
			pstmt.setString(2, bean.getpdate());
			pstmt.setString(3, bean.getpdetail());
			pstmt.setString(4, bean.getphashTag());
			pstmt.setInt(5, bean.getpidx());
			int cnt = pstmt.executeUpdate();
			if(cnt==1)
				flag=true;			
		} catch (Exception e) {	
			System.out.println("please select the row");
			//e.printStackTrace();
		}finally {
			pool.freeConnection(con,pstmt);
		}return flag;	
	}
	
	//Delete
	public boolean deletePlan(int pidx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "delete from planpro where pidx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pidx);
			if(pstmt.executeUpdate()==1)
				flag = true;
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con,pstmt);
		}return flag;
	}
	//reset primary key
	
	public boolean resetPidx() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		//ScheduleBean bean = new ScheduleBean();					
		try {
			con = pool.getConnection();
			sql = "call multi_query";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();					
	  }catch(Exception e) {
		  e.printStackTrace();
	  }finally {
		  pool.freeConnection(con, pstmt, rs);
	  }return flag;	
	}
	
	//search hashTag
	public Vector<ScheduleBean> searchPlan(String userid, String input) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from planpro where phashTag like '%"+ input +"%' and puser = '" + userid + "'"  ;
		boolean flag = false;
		
		Vector <ScheduleBean> vtable = new Vector<ScheduleBean>();
		
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ScheduleBean bean = new ScheduleBean();				
				bean.setpidx(rs.getInt(1));
				bean.setptitle(rs.getString(2));
				bean.setpdate(rs.getString(3));
				bean.setpdetail(rs.getString(4));
				bean.setphashTag(rs.getString(5));
				bean.setcdate(rs.getString(6));
				vtable.addElement(bean);
			}
		} catch (Exception e) {
			System.out.println("getting list error");
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}return vtable;
	}
	public Vector<ScheduleBean> printMonth(String userid, String pdate, String pdate2){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//select * from '테이블이름' where '날짜컬럼' between date('시작날짜') and date('끝날짜')+1
		//SELECT * from planpro where pdate BETWEEN "20191001" AND "20191020";
		Vector <ScheduleBean> vtable = new Vector<ScheduleBean>();
		System.out.println(userid);
		try {	
			String sql = "select * from planpro where puser = '"+userid+"' and pdate between ? and ?"; 
			
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);	
			pstmt.setString(1,pdate);
			pstmt.setString(2,pdate2);
			rs = pstmt.executeQuery();
			
			  while(rs.next()) {
				ScheduleBean bean = new ScheduleBean();
				bean.setpidx(rs.getInt(1));
				bean.setptitle(rs.getString(2));
				bean.setpdate(rs.getString(3));
				bean.setpdetail(rs.getString(4));
				vtable.addElement(bean);
			}
		} catch (Exception e) {
			System.out.println("print month error");
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}return vtable;
	}
	
	public Vector<ScheduleBean> todayPlan(String userid, String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector <ScheduleBean> vtable = new Vector<ScheduleBean>();
		
		try {
			con = pool.getConnection();
			sql = "select * from planpro where pdate = ? and puser = '" + userid + "'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ScheduleBean bean = new ScheduleBean();
				bean.setpidx(rs.getInt(1));
				bean.setptitle(rs.getString(2));
				bean.setpdate(rs.getString(3));
				bean.setpdetail(rs.getString(4));
				bean.setphashTag(rs.getString(5));
				bean.setcdate(rs.getString(6));
				vtable.addElement(bean);
			}
		} catch (Exception e) {
			System.out.println("getting list error");
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}return vtable;
	}
	
	}
	
	

	
		
		

	
	
	
	
	
	



