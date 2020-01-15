package team_p1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;



public class memoMgr {
	DBConnectionMgr pool;
	memoMgr(){
			pool = DBConnectionMgr.getInstance();
	}
	
	public Vector<memolistBean> loadMemolist(String userId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector <memolistBean> mlist = new Vector<memolistBean>();
		try {
			con = pool.getConnection();
			sql = "select * from memolist where userid = '"+ userId+"'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				memolistBean bean = new memolistBean();
				bean.setMemoidx(rs.getInt(1));
				bean.setContents(rs.getString(2));
				bean.setColor(rs.getString(3));
				bean.setUserid(rs.getString(4));
				
				
				mlist.add(bean);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}return mlist;
	}
	
	public void updateMemo(memolistBean bean){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		boolean flag = false;
		Vector <memolistBean> mlist = new Vector<memolistBean>();
		try {
			System.out.println("업데이트 실행");
			con = pool.getConnection();
			sql = "update memolist set color=?, contents=? where memoidx =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getColor());
			pstmt.setString(2, bean.getContents());
			pstmt.setInt(3, bean.getMemoidx());
			int cnt = pstmt.executeUpdate();
			if(cnt==1)
				flag=true;				
				mlist.add(bean);
			}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
	public Vector<memolistBean> loadSearch(String cont){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector <memolistBean> mlist = new Vector<memolistBean>();
		try {
			con = pool.getConnection();
			sql = "select * from memolist where contents like '%"+ cont+"%'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				memolistBean bean = new memolistBean();
				bean.setMemoidx(rs.getInt(1));
				bean.setContents(rs.getString(2));
				bean.setColor(rs.getString(3));
				bean.setUserid(rs.getString(4));
				
				
				mlist.add(bean);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}return mlist;
	}
	
	public void insertMemo(memolistBean bean){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		boolean flag = false;
		Vector <memolistBean> mlist = new Vector<memolistBean>();
		try {
			System.out.println("업데이트 실행");
			con = pool.getConnection();
			sql = "insert into memolist(contents, color, userid) values (?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getContents());
			pstmt.setString(2, bean.getColor());
			pstmt.setString(3, bean.getUserid());
			int cnt = pstmt.executeUpdate();
			if(cnt==1)
				flag=true;				
				mlist.add(bean);
			}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}

	
	public void deleteMemo(int memoIdx){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		boolean flag = false;
		Vector <memolistBean> mlist = new Vector<memolistBean>();
		try {
			System.out.println("업데이트 실행");
			con = pool.getConnection();
			sql = "delete from memolist where memoidx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memoIdx);
			int cnt = pstmt.executeUpdate();
			if(cnt==1)
				flag=true;				
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
	public void idxProcesure (){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		boolean flag = false;
		try {
			System.out.println("리스트 재정렬");
			con = pool.getConnection();
			sql = "call multiq";
			pstmt = con.prepareStatement(sql);
			int cnt = pstmt.executeUpdate();
			if(cnt==1)
				flag=true;				
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
}

