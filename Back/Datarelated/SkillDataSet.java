package Back.Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillDataSet {
	private Connection conn;
	
	public SkillDataSet() {
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
	
	//직업에 따른 초반 스킬 추가
	public void SGskill(String id, String class_n) {
		if(class_n.equals("01")) {
			addUskill(id, "11");
			addUskill(id, "12");
		}
		else if(class_n.equals("02")) {
			addUskill(id, "21");
			addUskill(id, "22");
		}
		else if(class_n.equals("03")) {
			addUskill(id, "31");
			addUskill(id, "32");
		}
		addUskill(id, "01");
		addUskill(id, "01");
		addUskill(id, "02");
	}
	public void BattleSkill(String id ,String class_n) {
		addUskill(id, "01");
		addUskill(id, "01");
		addUskill(id, "02");
		addUskill(id, "02");
		addUskill(id, "03");
		addUskill(id, "04");
		addUskill(id, "05");
		if(class_n.equals("01")) {
			addUskill(id, "11");
			addUskill(id, "11");
			addUskill(id, "12");
			addUskill(id, "12");
			addUskill(id, "13");
			addUskill(id, "13");
			addUskill(id, "14");
			addUskill(id, "15");
		}
		else if(class_n.equals("02")) {
			addUskill(id, "21");
			addUskill(id, "21");
			addUskill(id, "22");
			addUskill(id, "22");
			addUskill(id, "23");
			addUskill(id, "23");
			addUskill(id, "24");
			addUskill(id, "25");
		}
		else if(class_n.equals("03")) {
			addUskill(id, "31");
			addUskill(id, "31");
			addUskill(id, "32");
			addUskill(id, "32");
			addUskill(id, "33");
			addUskill(id, "33");
			addUskill(id, "34");
			addUskill(id, "35");
		}
		
	}
	//스킬 개수 확인
	public int getNum(String id) {
		int num = 0;
		try {
    		String sql = "select count(*) from Usingskill\n"+
    				"where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		if(rs.next()) {
    			num = rs.getInt(1);
    		}		
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return num;
	}
	
	//스킬 추가
	public void addUskill(String id, String skill_num) {
		int num = getNum(id)+1;
		try {
    		String sql = "insert into Usingskill(u_id, s_num, id)" +
    				"values (?, ?, ?)";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		pstmt.setInt(2, num);
    		pstmt.setString(3, skill_num);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	//스킬 삭제
	public void deleteUskill(String id) {
		if(this.isSkillOverlap(id)) {
			try {
	    		String sql = "delete from Usingskill\n"+
	    					"where u_id = ?";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, id);
	    		pstmt.executeUpdate();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
	}
	
	//스킬 섞어서 랜덤으로 보여주기
	public Skill[] randUskill(String id, int card_num) {
		int i=0;
		Skill randUs[] = new Skill[card_num];
    	try {
    		String sql = "select top (?) * from Usingskill\n"+
    					"where u_id = ?\n"+
    					"order by newid()";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, card_num);
    		pstmt.setString(2, id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    				randUs[i] = new Skill("", "", "", "", 0, 0);
    				addSkill(randUs[i],rs.getString("id"));
    				i++;   				
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return randUs;
	}
	//배틀 시에 사용하는 함수 Usingskill에서 카드 하나를 뽑아내며 삭제한다.
	public Skill SetUskill(String id) {
		Skill Us = new Skill("", "", "", "", 0, 0);
		int s_num = 0;
    	try {
    		String sql = "select top 1 * from Usingskill\n"+
    					"where u_id = ?\n"+
    					"order by newid()";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();		
    		while(rs.next()) {
    			addSkill(Us,rs.getString("id"));
    			s_num = rs.getInt("s_num");
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	deleteUoneskill(id, s_num);
    	return Us;
	}
	//하나 삭제하는 함수
	public void deleteUoneskill(String id, int snum) {
		if(this.isSkillOverlap(id)) {
			try {
	    		String sql = "delete from Usingskill\n"+
	    					"where u_id = ?, s_num = ?";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, id);
	    		pstmt.setInt(2, snum);
	    		pstmt.executeUpdate();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
	}
	//보상 스킬
	public Skill rewardSkill(String class_no) {
		Skill rewardskill = null;
		if(class_no.equals("01")) {//전사
			try {
	    		String sql = "select top 1 * from skill\n"
	    				+ "where ((id>=01 and id<=10) or (id>=11 and id<=20)) and not id in('03')\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    				rewardskill = new Skill("", "", "", "", 0, 0);
	    				addSkill(rewardskill,rs.getString("id"));
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else if(class_no.equals("02")) {//마법사
			try {
	    		String sql = "select top 1 * from skill\n"
	    				+ "where ((id>=01 and id<=10) or (id>=21 and id<=30)) and not id in('03')\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    				rewardskill = new Skill("", "", "", "", 0, 0);
	    				addSkill(rewardskill,rs.getString("id"));
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else if(class_no.equals("03")) {//도적
			try {
	    		String sql = "select top 1 * from skill\n"
	    				+ "where ((id>=01 and id<=10) or (id>=31 and id<=40)) and not id in('03')\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    				rewardskill = new Skill("", "", "", "", 0, 0);
	    				addSkill(rewardskill,rs.getString("id"));
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		return rewardskill;
	}
	
	//스킬 정보 확인
	public void addSkill(Skill skill, String skill_num) {
    	try {
    		String sql = "select * from skill where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.valueOf(skill_num));
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			skill.setId(rs.getString("id"));
    			skill.setName(rs.getString("name"));
    			skill.setType(rs.getString("type"));
    			skill.setExplan(rs.getString("explan"));
    			skill.setEffect(rs.getInt("effect"));
    			skill.setUmana(rs.getInt("use_mana"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public Skill infoSkill(String skill_num) {
		Skill infoS = new Skill("", "", "", "", 0, 0);
    	try {
    		String sql = "select * from skill where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.valueOf(skill_num));
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			infoS.setId(rs.getString("id"));
    			infoS.setName(rs.getString("name"));
    			infoS.setType(rs.getString("type"));
    			infoS.setExplan(rs.getString("explan"));
    			infoS.setEffect(rs.getInt("effect"));
    			infoS.setUmana(rs.getInt("use_mana"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return infoS;
	}
	
	//스킬 효과
	public int effectSkill(Skill skill,int Ap) {
		if(skill.getType().equals("S_attack")) {
			return skill.getEffect()+ Ap;
		}
		else if(skill.getType().equals("W_attack")) {
			return skill.getEffect()+ Ap;
		}
		else if(skill.getType().equals("Defense")) {
			return skill.getEffect()+ Ap;
		}
		else{
			return skill.getEffect();
		}
	}
	
	//스킬 저장 데이터 있는지 확인
    public boolean isSkillOverlap(String id) {
    	String sql = "select u_id from Usingskill";
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
    //상점 스킬 뽑기
    public String[] Storeskill(String class_no) {
    	String skill[] = {null, null, null};
    	int i = 0;
		if(class_no.equals("01")) {//전사
			try {
	    		String sql = "select top 3 * from skill\n"
	    				+ "where (id>=01 and id<=10) or (id>=11 and id<=20) and not id in(3)\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    			skill[i] = rs.getString("id");
	    			i++;
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else if(class_no.equals("02")) {//마법사
			try {
	    		String sql = "select top 1 * from skill\n"
	    				+ "where (id>=01 and id<=10) or (id>=21 and id<=30) and not id in(3)\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    			skill[i] = rs.getString("id");
	    			i++;
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else if(class_no.equals("03")) {//도적
			try {
	    		String sql = "select top 1 * from skill\n"
	    				+ "where (id>=01 and id<=10) or (id>=31 and id<=40) and not id in(3)\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    			skill[i] = rs.getString("id");
	    			i++;
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		return skill;
    }
}
