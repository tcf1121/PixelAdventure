package Back.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import Back.Datarelated.Ability;
import Back.Datarelated.Dungeon;
import Back.Datarelated.Equip;
import Back.Datarelated.Protocol;
import Back.Datarelated.Skill;
import Back.Datarelated.Store;
import Back.Datarelated.User;

public class MySocketClient {
	
	private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    
    public MySocketClient() throws IOException {
        socket = new Socket("172.29.229.169", 8500);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    public boolean updateState() throws IOException, ClassNotFoundException {
    	String response = (String) in.readObject();
    	System.out.println(response); // 필요에 따라 응답 처리
    	
        return response.equals("UPDATE_SUCCESS");
    }
    
    public void sendChatMessage(String message) throws IOException, ClassNotFoundException {
        String request = "CHAT#" + message;
        out.writeObject(request);
        // 서버로부터 받은 응답을 처리하는 로직을 추가할 수 있습니다.
    }
    
    public void sendConsultMessage(String message) throws IOException, ClassNotFoundException {
        String request = "Consult#" + message;
        out.writeObject(request);
        // 서버로부터 받은 응답을 처리하는 로직을 추가할 수 있습니다.
    }
    
    
    public boolean isIdOverlap(String id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("isIdOverlap", id);
        out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response); // 필요에 따라 응답 처리
        return response.equals("CHEAK_SUCCESS");
    }
    public boolean Join(User user) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("Join", user);
    	out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response);
        return response.equals("JOIN_SUCCESS");
    }
    public boolean ChecklogIn(String id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("checklogin", id);
        out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response); // 필요에 따라 응답 처리
        return response.equals("CHEAK_SUCCESS");
    }
    public boolean logIn(User user) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("login", user);
    	out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response);
        if(response.equals("LOGIN_SUCCESS")) {
        	out.writeUTF(user.getId());
        }
        return response.equals("LOGIN_SUCCESS");
    }
    public boolean logOut(User user) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("logout", user);
    	out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response);

        return response.equals("LOGOUT_SUCCESS");
    }
    public User getUser(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getUser", id);
    	out.writeObject(pr);
        User user = (User) in.readObject();
        System.out.println(user.getId()+","+user.getNickName());
        return user;
    }
    public int get_exp(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getExp", id);
    	out.writeObject(pr);
        int exp = (int) in.readObject();
        return exp;
    }
    public void deleteNAbility(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("deleteNAbility", id);
    	out.writeObject(pr);
    	System.out.println("deleteNAbility_SUCCESS");
    }
    public void setNAbility(String Class_Id, String id) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, Class_Id);
    	Protocol pr = new Protocol("setNAbility", info);
    	out.writeObject(pr);
    	System.out.println("setNAbility_SUCCESS");
    }
    public void setBAbility(String Class_Id, String id) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, Class_Id);
    	Protocol pr = new Protocol("setBAbility", info);
    	out.writeObject(pr);
    	System.out.println("setBAbility_SUCCESS");
    }
    public Ability getNAbility(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getNAbility", id);
    	out.writeObject(pr);
    	System.out.println("getNAbility_SUCCESS");
    	Ability ability = (Ability) in.readObject();
        return ability;
    }
    public void DeleteMap(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("DeleteMap", id);
    	out.writeObject(pr);
    	System.out.println("DeleteMap_SUCCESS");
    }
    public void setMap(String id, int floor) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, floor);
    	Protocol pr = new Protocol("setMap", info);
    	out.writeObject(pr);
    	System.out.println("setMap_SUCCESS");
    }
    public void setClear(String id, int location) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, location);
    	Protocol pr = new Protocol("setClear", info);
    	out.writeObject(pr);
    	System.out.println("setClear_SUCCESS");
    }
    public int getStartpoint(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getStartpoint", id);
    	out.writeObject(pr);
        int startpoint = (int) in.readObject();
        return startpoint;
    }
    public void deleteUskill(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("deleteUskill", id);
    	out.writeObject(pr);
    	System.out.println("deleteUskill_SUCCESS");
    }
    public void Equipped_Start(String id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("Equipped_Start", id);
    	out.writeObject(pr);
    	System.out.println("Equipped_Start_SUCCESS");
    }
    public Equip rewardEquip(int floor) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("rewardEquip", floor);
        out.writeObject(pr);
        Equip equip = (Equip)in.readObject();
        return equip;
    }
    public void Equipped(String id, String e_id) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, e_id);
    	Protocol pr = new Protocol("Equipped", info);
    	out.writeObject(pr);
    	System.out.println("Equipped_SUCCESS");
    }
    public void SGskill(String Class_Id, String id) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, Class_Id);
    	Protocol pr = new Protocol("SGskill", info);
    	out.writeObject(pr);
    	System.out.println("SGskill_SUCCESS");
    }
    public void addUskill(String id, String skill_num) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, skill_num);
    	Protocol pr = new Protocol("addUskill", info);
    	out.writeObject(pr);
    	System.out.println("addUskill_SUCCESS");
    }
    public Skill[] randUskill(String id, int card_num) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, card_num);
    	Protocol pr = new Protocol("randUskill", info);
        out.writeObject(pr);
        Skill[] skill = (Skill[])in.readObject();
        return skill;
    }
    public Skill rewardSkill(String class_no) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("rewardSkill", class_no);
        out.writeObject(pr);
        Skill skill = (Skill)in.readObject();
        return skill;
    }
    public void settingStore(String Class_Id, String id) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, Class_Id);
    	Protocol pr = new Protocol("settingStore", info);
    	out.writeObject(pr);
    	System.out.println("settingStore_SUCCESS");
    }
    public void SaveAP(String id, Ability ab) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, ab);
    	Protocol pr = new Protocol("SaveAP", info);
    	out.writeObject(pr);
    	System.out.println("SaveAP_SUCCESS");
    }
    public Ability LoadAP(String id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("LoadAP", id);
        out.writeObject(pr);
        Ability ability = (Ability) in.readObject();
        return ability;
    }
    public boolean isSaveAP(String id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("isSaveAP", id);
        out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response); // 필요에 따라 응답 처리
        return response.equals("CHEAK_SUCCESS");
    }
    public Dungeon getDungeon(String id, int location) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, location);
        Protocol pr = new Protocol("getDungeon", info);
        out.writeObject(pr);
        Dungeon dungeon = (Dungeon) in.readObject();
        return dungeon;
    }
    public Store getStore(String id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("getStore", id);
        out.writeObject(pr);
        Store store = (Store) in.readObject();
        return store;
    }
    public int[] getEquip(String id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("getEquip", id);
        out.writeObject(pr);
        int[] gEquip = (int[]) in.readObject();
        return gEquip;
    }
    public int[] getSkill(String id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("getSkill", id);
        out.writeObject(pr);
        int[] gSkill = (int[]) in.readObject();
        return gSkill;
    }
    public void addEquip(Equip equip, String equip_num) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(equip_num, equip);
    	Protocol pr = new Protocol("addEquip", info);
    	out.writeObject(pr);
    }
    public Equip infoEquip(String equip_num) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("infoEquip", equip_num);
    	out.writeObject(pr);
    	Equip infoE = (Equip)in.readObject();
    	return infoE;
    }
    public void addSkill(Skill skill, String skill_num) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(skill_num, skill);
    	Protocol pr = new Protocol("addSkill", info);
    	out.writeObject(pr);
    }
    public Skill infoSkill(String skill_num) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("infoSkill", skill_num);
    	out.writeObject(pr);
    	Skill infoS = (Skill)in.readObject();
    	return infoS;
    }
    public void buyPotion(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buyPotion", u_id);
    	out.writeObject(pr);
    	System.out.println("buyPotion_SUCCESS");
    }
    public void buyManaskill(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buyManaskill", u_id);
    	out.writeObject(pr);
    	System.out.println("buyManaskill_SUCCESS");
    }
    public void buySkill_card(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buySkill_card", u_id);
    	out.writeObject(pr);
    	System.out.println("buySkill_card_SUCCESS");
    }
    public void buySkill1(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buySkill1", u_id);
    	out.writeObject(pr);
    	System.out.println("buySkill1_SUCCESS");
    }
    public void buySkill2(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buySkill2", u_id);
    	out.writeObject(pr);
    	System.out.println("buySkill2_SUCCESS");
    }
    public void buySkill3(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buySkill3", u_id);
    	out.writeObject(pr);
    	System.out.println("buySkill3_SUCCESS");
    }
    public void buyEquipment1(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buyEquipment1", u_id);
    	out.writeObject(pr);
    	System.out.println("buyEquipment1_SUCCESS");
    }
    public void buyEquipment2(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buyEquipment2", u_id);
    	out.writeObject(pr);
    	System.out.println("buyEquipment2_SUCCESS");
    }
    public void buyEquipment3(String u_id) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("buyEquipment3", u_id);
    	out.writeObject(pr);
    	System.out.println("buyEquipment3_SUCCESS");
    }
    public int effectSkill(Skill skill,int Ap)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(Integer.toString(Ap), skill);
    	Protocol pr = new Protocol("effectSkill", info);
    	out.writeObject(pr);
        int effect = (int) in.readObject();
        return effect;
    }
    public void exp_up(String id, int get_exp) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, get_exp);
    	Protocol pr = new Protocol("exp_up", info);
    	out.writeObject(pr);
    	System.out.println("exp_up_SUCCESS");
    }
    public String getRoomnum() throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getRoomnum", null);
    	out.writeObject(pr);
        String roomnum = (String) in.readObject();
        return roomnum;
    }
    public void makeRoom(String roomnum, String hostid)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(hostid, roomnum);
    	Protocol pr = new Protocol("makeRoom", info);
    	out.writeObject(pr);
    }
    public boolean CheckRoom(String roomnum)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("CheckRoom", roomnum);
    	out.writeObject(pr);
        boolean response = (boolean) in.readObject();
		return response;
    }
    public boolean CheckFull(String roomnum)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("CheckFull", roomnum);
    	out.writeObject(pr);
        boolean response = (boolean) in.readObject();
		return response;
    }
    public void EnterRoom(String roomnum, String guestid)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(guestid, roomnum);
    	Protocol pr = new Protocol("EnterRoom", info);
    	out.writeObject(pr);
    }
    public void deleteRoom(String roomnum)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("deleteRoom", roomnum);
    	out.writeObject(pr);
    }
    public String getEId(String roomnum, String myid)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(myid, roomnum);
    	Protocol pr = new Protocol("getEId", info);
    	out.writeObject(pr);
    	return (String)in.readObject();
    }
    public int getTurn(String roomnum)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getTurn", roomnum);
    	out.writeObject(pr);
    	return (int)in.readObject();
    }
    public void setTurn(String roomnum, int turn)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(roomnum, turn);
    	Protocol pr = new Protocol("setTurn", info);
    	out.writeObject(pr);
    }
    public boolean isSkillOverlap(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("isSkillOverlap", id);
    	out.writeObject(pr);
    	return (boolean)in.readObject();
    }
    public Skill SetUskill(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("SetUskill", id);
    	out.writeObject(pr);
    	return (Skill)in.readObject();
    }
    public void BattleSkill(String id ,String class_n)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, class_n);
    	Protocol pr = new Protocol("BattleSkill", info);
    	out.writeObject(pr);
    }
    public void bstate(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("bstate", id);
    	out.writeObject(pr);
    }
    public void set_attack(String id, int attack)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, attack);
    	Protocol pr = new Protocol("set_attack", info);
    	out.writeObject(pr);
    }
    public int get_attack(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("get_attack", id);
    	out.writeObject(pr);
    	return (int)in.readObject();
    }
    public void set_defence(String id, int attack)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, attack);
    	Protocol pr = new Protocol("set_defence", info);
    	out.writeObject(pr);
    }
    public int get_defence(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("get_defence", id);
    	out.writeObject(pr);
    	return (int)in.readObject();
    }
    public void startchat(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("startchat", id);
    	out.writeObject(pr);
    }
    public void deletechat(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("deletechat", id);
    	out.writeObject(pr);
    }
    public void sendMessage(String id, String Message)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, Message);
    	Protocol pr = new Protocol("sendMessage", info);
    	out.writeObject(pr);
    }
    public String getMessage(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getMessage", id);
    	out.writeObject(pr);
    	return (String)in.readObject();
    }
    public void endServer() {
    	try {
			if (socket != null) socket.close();
			System.out.println("서버연결종료");
		} catch (IOException e) {
			System.out.println("소켓통신에러");
		}
    }
}
