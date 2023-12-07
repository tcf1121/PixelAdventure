 package Back.Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDataSet {

	private Connection conn;
	
	public RoomDataSet() {
        try {
        	//JDBC Driver 등록
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            //연결하기
            String connectionUrl = 
            		"jdbc:sqlserver://localhost:1433;"
            				+"database=rogue;"
            				+"encrypt=false;"
            				+"user=tcf12;"
            				+"password=dnlsxjaks!12;";
            conn = DriverManager.getConnection(connectionUrl);
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
    public boolean CheckRoom(String roomnum) {
    	String sql = "select roomnum from battle";
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			if(rs.getString("roomnum").equals(roomnum)) {
    				rs.close();
    				pstmt.close();
    				return true;
    			}
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
	public String getRoomnum() {
		String roomnum = "";
		do {
			try {
	    		String sql = "	declare @code char(5) \n"
	    				+ "	set @code = (\n"
	    				+ "	select c1 as [text()]\n"
	    				+ "	FROM (  select top(5) c1 \n"
	    				+ "	FROM (\r\n"
	    				+ "	VALUES    ('A'), ('B'), ('C'), ('D'), ('E'), ('F'), ('G'),\n"
	    				+ "	('H'), ('I'), ('J'),   ('K'), ('L'), ('M'), ('N'), ('O'),\n"
	    				+ "	('P'), ('Q'), ('R'), ('S'), ('T'),   ('U'), ('V'), ('W'),\n"
	    				+ "	('X'), ('Y'), ('Z'), ('0'), ('1'), ('2'), ('3'),   ('4'),\n"
	    				+ "	('5'), ('6'), ('7'), ('8'), ('9')  ) AS T1(c1)\n"
	    				+ "	ORDER BY ABS(CHECKSUM(NEWID()))\n"
	    				+ "	) AS T2 FOR XML PATH('')\n"
	    				+ "	);\r\n"
	    				+ "	select @code";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();
	    		
	    		if(rs.next()) {
	    			roomnum = rs.getString(1);
	    		}		
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		} while (CheckRoom(roomnum));
			
		return roomnum;
	}

    
	public void makeRoom(String roomnum, String hostid) {
		try {
    		String sql = "insert into battle(roomnum, host)" +
    				"values (?, ?)";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, roomnum);
    		pstmt.setString(2, hostid);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    public boolean CheckBstate(String id) {
    	String sql = "select u_id from Bstate";
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			if(rs.getString("u_id").equals(id)) {
    				rs.close();
    				pstmt.close();
    				return true;
    			}
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
	
	public String getEId(String roomnum, String myid) {
		String[] id = {null, null}; 
		try {
    		String sql = "select * from battle\n"+
    					"where roomnum = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, roomnum);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			id[0] = rs.getString("host");
    			id[1] = rs.getString("guest");
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		if(id[0].equals(myid)) {
			return id[1];
		}
		else {
			return id[0];
		}
	}
	public int getTurn(String roomnum) {
		int turn = 0; 
		try {
    		String sql = "select * from battle\n"+
    					"where roomnum = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, roomnum);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			turn = rs.getInt("turn");
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return turn;
	}
	public void setTurn(String roomnum, int turn) {
		try {
    		String sql = "update battle\n"
    				+ "set turn = ?\n"
    				+ "where roomnum = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, turn);
    		pstmt.setString(2, roomnum);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void EnterRoom(String roomnum, String guestid) {
		try {
    		String sql = "update battle\n"
    				+ "set guest = ?\n"
    				+ "where roomnum = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, guestid);
    		pstmt.setString(2, roomnum);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public boolean CheckFull(String roomnum) {
		String guest = null;
		try {
    		String sql = "select guest from battle\n"+
    					"where roomnum = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, roomnum);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			guest = rs.getString("guest");
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		if(guest == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public void deleteRoom(String roomnum) {
    	try {
		String sql = "delete from battle where roomnum=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, roomnum);
		pstmt.executeUpdate();
		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public void bstate(String id) {
		if(CheckBstate(id)) {
			try {
	    		String sql ="update Bstate\n"+
	    				"set attack = 0, defence = 0, die = 0" + 
	    				"where u_id = ?";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, id);
	    		pstmt.executeUpdate();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else {
			try {
	    		String sql = "insert into Bstate(u_id)" +
	    				"values (?)";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, id);
	    		pstmt.executeUpdate();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
	}
	
	public int get_attack(String id) {
		int attack = 0;
		try {
    		String sql = "select attack from Bstate\n"+
    					"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			attack = rs.getInt("attack");
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		set_attack(id, 0);
		return attack;
	}
	public void set_attack(String id, int attack) {
		try {
    		String sql ="update Bstate\n"+
    				"set attack = ?" + 
    				"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, attack);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public int get_defence(String id) {
		int defence = 0;
		try {
    		String sql = "select defence from Bstate\n"+
    					"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			defence = rs.getInt("defence");
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		set_defence(id, 0);
		return defence;
	}
	public void set_defence(String id, int defence) {
		try {
    		String sql ="update Bstate\n"+
    				"set defence = ?" + 
    				"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, defence);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void startchat(String id) {
		try {
    		String sql = "insert into Chatting(u_id)" +
    				"values (?)";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void deletechat(String id) {
    	try {
		String sql = "delete from Chatting where u_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void sendMessage(String id, String Message) {
		try {
    		String sql ="update Chatting\n"+
    				"set chat = ?" + 
    				"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, Message);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public String getMessage(String id) {
		String chat = null;
		try {
    		String sql = "select chat from Chatting\n"+
    					"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			chat = rs.getString("chat");
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		sendMessage(id, null);
		return chat;
	} 
}
