package Back.Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

public class DungeonDataSet {
	private Connection conn;
	
	public DungeonDataSet() {
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
	public Dungeon getDungeon(String id, int location) {
		Dungeon dungeon = new Dungeon(0, 0, 0, ' ');
		try {
	    	String sql = "select * from dungeon\n"
	    			+ "where location=? and u_id = ?\n";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, location);
    		pstmt.setString(2, id);
    		ResultSet rs = pstmt.executeQuery();
	    		
	    	while(rs.next()) {
	    		dungeon.setDifficulty(rs.getInt("difficulty"));
	    		dungeon.setClear(rs.getInt("clear"));
	    		dungeon.setMaptype(rs.getString("maptype").charAt(0));	    		
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return dungeon;
	}
	public int getStartpoint(String id) {
		int start = 0;
		try {
	    	String sql = "select location from dungeon\n"
	    			+ "where start=1 and u_id = ?\n";
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
	    	ResultSet rs = pstmt.executeQuery();	    		
	    	while(rs.next()) {
	    			start = rs.getInt("location");
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return start;
	}
	
	public int getClear(String id,int location) {
		int clear = 0;
		try {
	    	String sql = "select clear from dungeon\n"
	    			+ "where location=? and u_id = ?\n";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, location);
    		pstmt.setString(2, id);
    		ResultSet rs = pstmt.executeQuery();
	    		
	    	while(rs.next()) {
	    			clear = rs.getInt("clear");
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return clear;
	}
	
	public void setClear(String id, int location) {
		try {
    		String sql = "update dungeon\n"
    				+ "set clear = 1\n"
    				+ "where location = ? and u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, location);
    		pstmt.setString(2, id);
    		pstmt.executeUpdate();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public int getDifficulty(String id, int location) {
		int difficulty = 0;
		try {
	    	String sql = "select difficulty from dungeon\n"
	    			+ "where location=? and u_id = ?\n";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, location);
    		pstmt.setString(2, id);
    		ResultSet rs = pstmt.executeQuery();
	    		
	    	while(rs.next()) {
	    		difficulty = rs.getInt("difficulty");
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return difficulty;
	}
	
	public char getMtype(String id, int location) {
		char maptype = 0;
		try {
	    	String sql = "select maptype from dungeon\n"
	    			+ "where location=? and u_id = ?\n";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, location);
    		pstmt.setString(2, id);
    		ResultSet rs = pstmt.executeQuery();
	    		
	    	while(rs.next()) {
	    		maptype = rs.getString("maptype").charAt(0);
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return maptype;
	}
	
	public void setMap(String id, int floor) {
		HashMap<Integer, Dungeon> dungeon = new HashMap<Integer, Dungeon>();
		int count = 5;
		int location[] = new int[count];
		int set_d = 0;
		int set_a = 0;
		Random r = new Random();
		for(int i=0; i<count; i++){
			location[i] = r.nextInt(5); // 0 ~ 4랜덤으로 뽑음
			for(int j=0; j<i; j++){
				if(location[i] == location[j]){
					i--;
				}
			}
		}
		
		if(floor == 1) {
			for(int i=0; i<count; i++) {
				
				if(location[i]==0) {dungeon.put(0, new Dungeon(1, 0, r.nextInt(100)==0?0:1, 's'));}
				else {
					if(set_d == 0) {
						dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:1, 'd'));
						set_d = 1;
					}
					else {
						dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:1, 'r'));
					}
					
				}
			}
			
		}
		else if(floor == 2 || floor == 3) {
			dungeon.put(location[0], new Dungeon(0, 0, r.nextInt(100)==0?0:((floor-1)*2)+ r.nextInt(2), 'd'));
			dungeon.put(location[1], new Dungeon(1, 0, r.nextInt(100)==0?0:((floor-1)*2)+ r.nextInt(2), 'a'));
			for(int i=2; i<count;i++) {
					dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:((floor-1)*2)+ r.nextInt(2), 'r'));
				}
		}
		else if(floor == 4) {
			int store = r.nextInt(2);
			if(store == 1) {store = 4;}
			for(int i=0; i<count;i++) {
				if(location[i]==store) {dungeon.put(location[i], new Dungeon(0, 1, r.nextInt(100)==0?0:((floor-1)*2)+ r.nextInt(2), 's'));}
				else {
					if(set_d == 0) {
						dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:((floor-1)*2)+ r.nextInt(2), 'd'));
						set_d = 1;
					}
					else if(set_a == 0) {
						dungeon.put(location[i], new Dungeon(1, 0, r.nextInt(100)==0?0:((floor-1)*2)+ r.nextInt(2), 'a'));
						set_a = 1;
					}
					else {
						dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:((floor-1)*2)+ r.nextInt(2), 'r'));
					}
					
				}
			}
		}
		else if(floor == 5) {
			int boss = r.nextInt(2);
			if(boss == 1) {boss = 4;}
			for(int i=0; i<count;i++) {
				if(boss==0) {
					if(location[i]==0) {dungeon.put(location[i], new Dungeon(0, 0, 10, 'b'));}
					else if (location[i] == 1) {dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:9, 'e'));}
					else {
						if(set_a == 0) {
							dungeon.put(location[i], new Dungeon(1, 0, r.nextInt(100)==0?0:8, 'a'));
							set_a = 1;
						}
						else {
							dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:8, 'r'));
						}
					}
				}
				else {
					if(location[i]==4) {dungeon.put(location[i], new Dungeon(0, 0, 10, 'b'));}
					else if (location[i] == 3) {dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:9, 'e'));}
					else {
						if(set_a == 0) {
							dungeon.put(location[i], new Dungeon(1, 0, r.nextInt(100)==0?0:8, 'a'));
							set_a = 1;
						}
						else {
							dungeon.put(location[i], new Dungeon(0, 0, r.nextInt(100)==0?0:8, 'r'));
						}
					}
				}
			}
		}
		for(int i =0; i<count; i++) {
	    	try {
	    		String sql = ""+
	    				"insert into dungeon(u_id, location, maptype, start, clear, difficulty)" +
	    				"values (?, ?, ?, ?, ?, ?)";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, id);
	    		pstmt.setInt(2, i);
	    		pstmt.setString(3, String.valueOf(dungeon.get(i).getMaptype()));
	    		pstmt.setInt(4, dungeon.get(i).getStart());
	    		pstmt.setInt(5, dungeon.get(i).getClear());
	    		pstmt.setInt(6, dungeon.get(i).getDifficulty());
	    		pstmt.executeUpdate();
	    		pstmt.close();
	        	}catch(Exception e) {
	        		e.printStackTrace();
	        	}
		}

	}
	
	public void DeleteMap(String id) {
	       try {
	    	   String sql = "delete from dungeon\n"+
	    			   		"where u_id = ?";
	    	   PreparedStatement pstmt = conn.prepareStatement(sql);
	    	   pstmt.setString(1, id);
	    	   pstmt.executeUpdate();
	    	   pstmt.close();   	   
	       }catch (SQLException e) {
	    	   e.printStackTrace();
	       }
	}
}
