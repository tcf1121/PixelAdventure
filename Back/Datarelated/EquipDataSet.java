package Back.Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipDataSet {
	private Connection conn;
	
	public EquipDataSet() {
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
	
	public void Equipped_Start(String id) {//첫 시작시 초기화
		if(this.isEqOverlap(id)) {
			try {
	    		String sql ="update Equipped\n"+
	    				" set Weapon = 0, Shield = 0, Armor = 0, Shoes = 0,"+
	    				" strength=0, defense=0, health=0, mana = 0\n" + 
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
	    		String sql = "insert into Equipped(u_id)" +
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
	
	public void Equipped(String id, String e_id) {
		String type = this.getType(Integer.parseInt(e_id));
		int effect = this.getEffect(Integer.parseInt(e_id));
		switch (type) {
		case "Weapon":
			if(this.getWeapon(id)<Integer.parseInt(e_id)) {
				this.setWeapon(e_id, Integer.parseInt(e_id));
				this.setStrength(id, effect);
			}
			break;
		case "Shield":
			if(this.getShield(id)<Integer.parseInt(e_id)) {
				this.setShield(e_id, Integer.parseInt(e_id));
				this.setDefense(id, effect);
			}			
			break;
		case "Armor":
			if(this.getArmor(id)<Integer.parseInt(e_id)) {
				this.setArmor(e_id, Integer.parseInt(e_id));
				this.setHealth(id, effect);
			}			
			break;
		case "Shoes":
			if(this.getShoes(id)<Integer.parseInt(e_id)) {
				this.setShoes(e_id, Integer.parseInt(e_id));
				this.setMana(id, effect);
			}			
			break;
		default:
			break;
		}
	}
	
	public Equip rewardEquip(int floor) {
		int R1 = (floor*5)-5;
		int R2 = (floor*5);
		Equip rewardequip = null;
		try {
	    	String sql = "select top 1 * from equipment\n"
	    			+ "where id>? and id<?\n"
	    			+ "order by newid()";
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, R1);
	    	pstmt.setInt(2, R2);
	    	ResultSet rs = pstmt.executeQuery();	    		
	    	while(rs.next()) {
	    			rewardequip = new Equip("", "","","",0,"");
	    			addEquip(rewardequip,rs.getString("id"));
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return rewardequip;
	}
	public void addEquip(Equip equip, String equip_num) {
    	try {
    		String sql = "select * from equipment where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.valueOf(equip_num));
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			equip.setId(rs.getString("id"));
    			equip.setName(rs.getString("name"));
    			equip.setExplan(rs.getString("explan"));
    			equip.setEf_ex(rs.getString("ef_ex"));
    			equip.setEffect(rs.getInt("effect"));
    			equip.setRarity(rs.getString("rarity"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public Equip infoEquip(String equip_num) {
		Equip infoE = new Equip("", "","","",0,"");
    	try {
    		String sql = "select * from equipment where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.valueOf(equip_num));
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			infoE.setId(rs.getString("id"));
    			infoE.setName(rs.getString("name"));
    			infoE.setExplan(rs.getString("explan"));
    			infoE.setEf_ex(rs.getString("ef_ex"));
    			infoE.setEffect(rs.getInt("effect"));
    			infoE.setRarity(rs.getString("rarity"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return infoE;
	}
	public String getType(int num) {
		String type = "";
    	try {
    		String sql = "select type from equipment\n"+
    					"where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, num);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			type = rs.getString("type");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return type;
	}
	public int getEffect(int num) {
		int effect = 0;
    	try {
    		String sql = "select effect from equipment\n"+
    					"where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, num);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			effect = rs.getInt("effect");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return effect;
	}
	public int getWeapon(String id){
		int weapon = 0;
    	try {
    		String sql = "select Weapon from Equipped\n"+
    					"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			weapon = rs.getInt("Weapon");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return weapon;
	}
	public void setWeapon(String id, int e_id) {
    	try {
    		String sql ="update Equipped\n"+
    				" set Weapon = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, e_id);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public int getShield(String id){
		int shield = 0;
    	try {
    		String sql = "select Shield from Equipped\n"+
    					"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			shield = rs.getInt("Shield");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return shield;
	}
	public void setShield(String id, int e_id) {
    	try {
    		String sql ="update Equipped\n"+
    				" set Shield = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, e_id);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public int getArmor(String id){
		int armor = 0;
    	try {
    		String sql = "select Armor from Equipped\n"+
    					"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			armor = rs.getInt("Armor");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return armor;
	}
	public void setArmor(String id, int e_id) {
    	try {
    		String sql ="update Equipped\n"+
    				" set Armor = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, e_id);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public int getShoes(String id){
		int shoes = 0;
    	try {
    		String sql = "select Shoes from Equipped\n"+
    					"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		while(rs.next()) {
    			shoes = rs.getInt("Shoes");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return shoes;
	}
	public void setShoes(String id, int e_id) {
    	try {
    		String sql ="update Equipped\n"+
    				" set Shoes = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, e_id);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public void setStrength(String id, int num){
    	try {
    		String sql ="update Equipped\n"+
    				" set strength = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, num);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public void setDefense(String id, int num){
    	try {
    		String sql ="update Equipped\n"+
    				" set defense = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, num);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public void setHealth(String id, int num){
    	try {
    		String sql ="update Equipped\n"+
    				" set health = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, num);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	public void setMana(String id, int num){
    	try {
    		String sql ="update Equipped\n"+
    				" set mana = ?\n"
    				+ "where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, num);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
	}
	
    public boolean isEqOverlap(String id) {
    	String sql = "select u_id from Equipped";
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
    public String[] StoreEquip() {
    	String equip[] = {null, null, null};
    	int i = 0;
    	try {
    		String sql = "select top 3 * from equipment\n"+
    					"where id > 15\n"+
    					"order by newid()";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			equip[i] = rs.getString("id");
    			i++;
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return equip;
    }
}
