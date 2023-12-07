package Back.Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreDataSet {
    private Connection conn;
    private EquipDataSet equipData = new EquipDataSet();
    private SkillDataSet skillData = new SkillDataSet();
	public StoreDataSet() {
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
	
	public void settingStore(String u_id, String class_no) {
		String skill[] = skillData.Storeskill(class_no);
		String equip[] = equipData.StoreEquip();
		if(isStoreOverlap(u_id)) {
			try {
	    		String sql = "update store\n"
	    				+"set potion=0, manaskill=0, skill_card=0,"
	    				+ "skill1_buy=0, skill2_buy=0, skill3_buy=0,"
	    				+ "equipment1_buy=0, equipment2_buy=0, equipment3_buy=0,"
	    				+ "equipment1_id=?, equipment2_id=?, equipment3_id=?,"
	    				+ "skill1_id=?,skill2_id=?, skill3_id=?\n"
	    				+ "where u_id = ?";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, equip[0]);
	    		pstmt.setString(2, equip[1]);
	    		pstmt.setString(3, equip[2]);
	    		pstmt.setString(4, skill[0]);
	    		pstmt.setString(5, skill[1]);
	    		pstmt.setString(6, skill[2]);
	    		pstmt.setString(7, u_id);
	    		pstmt.executeUpdate();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else {
			try {
	    		String sql = "insert into store(u_id, equipment1_id,"
	    				+ "equipment2_id, equipment3_id, skill1_id,"
	    				+ " skill2_id, skill3_id)" +
	    				"values (?, ?, ?, ?, ?, ?, ?)";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, u_id);
	    		pstmt.setString(2, equip[0]);
	    		pstmt.setString(3, equip[1]);
	    		pstmt.setString(4, equip[2]);
	    		pstmt.setString(5, skill[0]);
	    		pstmt.setString(6, skill[1]);
	    		pstmt.setString(7, skill[2]);
	    		pstmt.executeUpdate();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
	}
	
	public Store getStore(String u_id) {
		Store store = new Store(0, 0, 0, 0, 0, 0, 0, 0, 0);
		try {
    		String sql = "select * from store\n"+
    				"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			store.setPotion(rs.getInt("potion"));
    			store.setManaskill(rs.getInt("manaskill"));
    			store.setSkill_card(rs.getInt("skill_card"));
    			store.setSkill1(rs.getInt("skill1_buy"));
    			store.setSkill2(rs.getInt("skill2_buy"));
    			store.setSkill3(rs.getInt("skill3_buy"));
    			store.setEquipment1(rs.getInt("equipment1_buy"));
    			store.setEquipment2(rs.getInt("equipment2_buy"));
    			store.setEquipment3(rs.getInt("equipment3_buy"));
    		}		
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return store;
	}
	public int[] getEquip(String u_id) {
		int equip[] = {0, 0, 0};
		try {
    		String sql = "select * from store\n"+
    				"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			equip[0] = rs.getInt("equipment1_id");
    			equip[1] = rs.getInt("equipment2_id");
    			equip[2] = rs.getInt("equipment3_id");
    		}		
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return equip;
	}
	public int[] getSkill(String u_id) {
		int skill[] = {0, 0, 0};
		try {
    		String sql = "select * from store\n"+
    				"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			skill[0] = rs.getInt("skill1_id");
    			skill[1] = rs.getInt("skill2_id");
    			skill[2] = rs.getInt("skill3_id");
    		}		
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return skill;
	}
	
	
	public void buyPotion(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set potion = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}

	public void buyManaskill(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set manaskill = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void buySkill_card(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set skill_card = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void buySkill1(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set skill1_buy = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void buySkill2(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set skill2_buy = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void buySkill3(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set skill3_buy = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void buyEquipment1(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set equipment1_buy = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void buyEquipment2(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set equipment2_buy = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
	public void buyEquipment3(String u_id) {
    	try {
    		String sql ="update store\n"+
    				" set equipment3_buy = 1\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, u_id);   		
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
    public boolean isStoreOverlap(String id) {
    	String sql = "select u_id from store";
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			if(rs.getString("u_id").equals(id)) {
    				pstmt.close();
    				return true;
    			}
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
}