package Back.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import Back.Datarelated.*;
import GUI.Manager.ManagerForm;

public class MasterServer extends Thread{
	public final int inport = 8500;
	Vector<Client> Clientlist = new Vector<>();
	ArrayList<String> IdList = new ArrayList<String>(); // 클라이언트의 닉네임을 저장해줍니다.
	ServerSocket masterserver;
	ManagerForm managerForm;
	public void setGUI(ManagerForm owner) {
		this.managerForm = owner;
	}
	
	public void run() {
		try {
			masterserver = new ServerSocket(inport);
			while (true) {
				Socket cs = masterserver.accept(); //cs: client socket
				Client csvr = new Client(cs);
				csvr.start();
				Clientlist.add(csvr);
			}
		} catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
	}
	
	public Vector<Client> getAllClient() {
		return Clientlist;
	}
	public void removeClient(Client client, String id) {
		//유저 로그아웃시 유저 목록에서 제거
		Clientlist.removeElement(client);
		IdList.remove(id);
		managerForm.tfuserlist.setText(null);
		managerForm.AppendUserList(IdList);
	}
	class Client extends Thread {
		private Socket clientSocket = null;
		private ObjectInputStream in = null;
		private ObjectOutputStream out = null;
		private CharDataSet charDataSet = new CharDataSet();
		private DungeonDataSet dungeonDataSet = new DungeonDataSet();
		private EquipDataSet equipDataSet = new EquipDataSet();
		private SkillDataSet skillDataSet =  new SkillDataSet();
		private StoreDataSet storeDataSet =  new StoreDataSet();
		private UserDataSet userDataSet = new UserDataSet();
		private RoomDataSet roomDataSet = new RoomDataSet();
		
	    String id;
	    String command;
	    User user;
        String Class_Id;
        int location;
        Ability ability;
        
		public Client(Socket sock) throws ClassNotFoundException, SQLException, IOException {
			clientSocket = sock;
			out = new ObjectOutputStream(clientSocket.getOutputStream());
	        in = new ObjectInputStream(clientSocket.getInputStream());
	        
//			try {
//				BufferedInputStream bis = new BufferedInputStream(sock.getInputStream());
//				in = new ObjectInputStream(bis);
//				BufferedOutputStream bos = new BufferedOutputStream(sock.getOutputStream());
//				out = new ObjectOutputStream(bos);
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		@Override
		public void run() {
//			try {
//				UserPacket o1;
//				while (true) { // the state machine, Simple: REQ-REPLY
//					o1 = (UserPacket)in.readObject();
//					//in.close();
//					System.out.println(o1.toString());
//					System.out.println(o1.getUId());
//					out.writeObject("OK");
//					//out.close();
//				}
//			} catch (Exception e) {
//				System.out.println("Exit.");
//			}
			try {
				
	            while (true) {
	            	Protocol pr = (Protocol) in.readObject();
	                command = pr.getCommand();	                
	                Protocol info;
					switch (command) {
//	                    case "CHAT":
//	                        String message = requestData[1];
//	                        System.out.println("메시지 수신: " + message);
//	                        // 채팅 메시지를 다른 클라이언트로 전송하는 로직을 추가합니다.
//	                        // 여기서는 수신한 메시지를 해당 클라이언트에 연결된 다른 클라이언트에게 보내는 방식으로 구현합니다.
//	                        sendChatMessageToOtherClients(message);
//	                        break;
	                    case "Join":
	                        userDataSet.addUsers((User)pr.getObject());
	                        out.writeObject("JOIN_SUCCESS");
	                        break;
	                    case "isIdOverlap":
	                        boolean isId = userDataSet.isIdOverlap((String)pr.getObject());
	                        out.writeObject(isId ?"CHEAK_SUCCESS": "CHEAK_FAILURE");
	                        break;
	                    case "checklogin":
	                    	boolean cklogin = userDataSet.CheckLogin((String)pr.getObject());
	                    	out.writeObject(cklogin?"CHEAK_SUCCESS": "CHEAK_FAILURE");
	                    	break;
	                    case "login":
	                    	user = (User)pr.getObject();
	                        boolean loginResult = userDataSet.Login(user.getId(), user.getPw());
	                        id = user.getId();
	                        if(loginResult) {
	                        	managerForm.tfuserlist.setText(null); 
	                        	IdList.add(id);
	            				managerForm.AppendUserList(IdList);
	                        }
	                        out.writeObject(loginResult ? "LOGIN_SUCCESS" : "LOGIN_FAILURE");
	                        break;
	                    case "logout":
	                    	user = (User)pr.getObject();
	                        boolean loginResult1 = userDataSet.LogOut(user.getId(), user.getPw());
	                        out.writeObject(loginResult1 ? "LOGOUT_SUCCESS" : "LOGOUT_FAILURE");
	                        break;
	                    case "getUser":
	                    	id = (String)pr.getObject();
	                        user = userDataSet.getUser(id);
	                        out.writeObject(user);
	                        break;
	                    case "getExp":
	                    	id = (String)pr.getObject();
	                        int exp= userDataSet.get_exp(id);
	                        out.writeObject(exp);
	                        break;
	                    case "deleteNAbility":
	                    	id = (String)pr.getObject();
	                        charDataSet.deleteNAbility(id);
	                        break;
	                    case "setNAbility":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	Class_Id = (String)info.getObject();
	                    	charDataSet.setNAbility(Class_Id, id);
	                        break;
	                    case "setBAbility":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	Class_Id = (String)info.getObject();
	                    	charDataSet.setBAbility(Class_Id, id);
	                        break;
	                    case "getNAbility":
	                    	id = (String)pr.getObject();
	                        ability = charDataSet.getNAbility(id);
	                        out.writeObject(ability);
	                        break;
	                    case "DeleteMap":
	                    	id = (String)pr.getObject();
	                        dungeonDataSet.DeleteMap(id);
	                        break;
	                    case "setMap":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	int floor = (int)info.getObject();
	                        dungeonDataSet.setMap(id, floor);
	                        break;
	                    case "setClear":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	location = (int)info.getObject();
	                        dungeonDataSet.setClear(id, location);
	                        break;
	                    case "getStartpoint":
	                    	id = (String)pr.getObject();
	                        int sp = dungeonDataSet.getStartpoint(id);
	                        out.writeObject(sp);
	                        break;
	                    case "Equipped_Start":
	                    	id = (String)pr.getObject();
	                    	equipDataSet.Equipped_Start(id);
	                        break;
	                    case "rewardEquip":
	                    	floor = (int)pr.getObject();
	                        Equip equip = equipDataSet.rewardEquip(floor);
	                        out.writeObject(equip);
	                        break;
	                    case "Equipped":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	String e_id = (String)info.getObject();
	                    	equipDataSet.Equipped(id, e_id);
	                        break;
	                    case "deleteUskill":
	                    	id = (String)pr.getObject();
	                        skillDataSet.deleteUskill(id);
	                        break;
	                    case "addUskill":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	String skill_num = (String)info.getObject();
	                        skillDataSet.addUskill(id, skill_num);
	                        break;
	                    case "effectSkill":
	                    	info = (Protocol)pr.getObject();
	                    	int ap = Integer.valueOf(info.getCommand());
	                    	Skill eskill = (Skill)info.getObject();
	                        int effect = skillDataSet.effectSkill(eskill, ap);
	                        out.writeObject(effect);
	                        break;
	                    case "randUskill":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	int card_num = (int)info.getObject();
	                        Skill[] skill = skillDataSet.randUskill(id, card_num);
	                        out.writeObject(skill);
	                        break;
	                    case "rewardSkill":
	                    	Class_Id = (String)pr.getObject();
	                        Skill rskill = skillDataSet.rewardSkill(Class_Id);
	                        out.writeObject(rskill);
	                        break;
	                    case "SGskill":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	Class_Id = (String)info.getObject();
	                        skillDataSet.SGskill(id, Class_Id);
	                        break;   

	                    case "settingStore":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	Class_Id = (String)info.getObject();
	                        storeDataSet.settingStore(id, Class_Id);
	                        break;
	                    case "isSaveAP":
	                        boolean isAPid = charDataSet.isSaveAP((String)pr.getObject());
	                        out.writeObject(isAPid ?"CHEAK_SUCCESS": "CHEAK_FAILURE");
	                        break;
	                    case "SaveAP":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	ability = (Ability)info.getObject();
	                        charDataSet.SaveAP(id, ability);
	                        break;
	                    case "LoadAP":
	                    	id = (String)pr.getObject();
	                    	ability = charDataSet.LoadAP(id);
	                    	out.writeObject(ability);
	                        break;
	                    case "getDungeon":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	location = (int)info.getObject();
	                    	Dungeon dungeon = dungeonDataSet.getDungeon(id, location);
	                    	out.writeObject(dungeon);
	                        break;
	                    case "getEquip":
	                    	id = (String)pr.getObject();
	                    	int[] Equipnum = storeDataSet.getEquip(id);
	                    	out.writeObject(Equipnum);
	                        break;
	                    case "getSkill":
	                    	id = (String)pr.getObject();
	                    	int[] Skillnum = storeDataSet.getSkill(id);
	                    	out.writeObject(Skillnum);
	                        break;
	                    case "getStore":
	                    	id = (String)pr.getObject();
	                    	Store store = storeDataSet.getStore(id);
	                    	out.writeObject(store);
	                        break;
	                    case "addEquip":
	                    	info = (Protocol)pr.getObject();
	                    	String equip_num = (String)pr.getObject();
	                    	Equip equip2 = (Equip)info.getObject();
	                    	equipDataSet.addEquip(equip2, equip_num);
	                        break;
	                    case "infoEquip":
	                    	String infoequip_num = (String)pr.getObject();
	                    	Equip infoE = equipDataSet.infoEquip(infoequip_num);
	                    	out.writeObject(infoE);
	                        break;
	                    case "addSkill":
	                    	info = (Protocol)pr.getObject();
	                    	String skill_num1 = info.getCommand();
	                    	Skill skill2 = (Skill)info.getObject();
	                    	skillDataSet.addSkill(skill2, skill_num1);
	                        break;
	                    case "infoSkill":
	                    	String infoSkill_num = (String)pr.getObject();
	                    	Skill infoS = skillDataSet.infoSkill(infoSkill_num);
	                    	out.writeObject(infoS);
	                        break;
	                    case "buyPotion":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buyPotion(id);
	                        break;
	                    case "buyManaskill":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buyManaskill(id);
	                        break;
	                    case "buySkill_card":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buySkill_card(id);
	                        break;
	                    case "buySkill1":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buySkill1(id);
	                        break;
	                    case "buySkill2":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buySkill2(id);
	                        break;
	                    case "buySkill3":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buySkill3(id);
	                        break;
	                    case "buyEquipment1":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buyEquipment1(id);
	                        break;
	                    case "buyEquipment2":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buyEquipment2(id);
	                        break;
	                    case "buyEquipment3":
	                    	id = (String)pr.getObject();
	                    	storeDataSet.buyEquipment3(id);
	                        break;
	                    case "exp_up":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	int expup = (int)info.getObject();
	                    	userDataSet.exp_up(id, expup);
	                        break;
	                    case "getRoomnum":
	                    	String roomnum = roomDataSet.getRoomnum();
	                    	out.writeObject(roomnum);
	                    	break;
	                    case "makeRoom":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	String mroomnum = (String)info.getObject();
	                    	roomDataSet.makeRoom(mroomnum, id);
	                        break;
	                    case "EnterRoom":
	                    	info = (Protocol)pr.getObject();
	                    	id = info.getCommand();
	                    	String eroomnum = (String)info.getObject();
	                    	roomDataSet.EnterRoom(eroomnum, id);
	                        break;
	                    case "deleteRoom":
	                    	roomDataSet.deleteRoom((String)pr.getObject());
	                    	break;
	                    case "CheckRoom":
	                        boolean isroom = roomDataSet.CheckRoom((String)pr.getObject());
	                        out.writeObject(isroom);
	                        break;
	                    case "startchat":
	                    	roomDataSet.startchat((String)pr.getObject());
	                        break;
	                    case "deletechat":
	                    	roomDataSet.deletechat((String)pr.getObject());
	                    	break;
	                    case "CheckFull":
	                        boolean fullroom = roomDataSet.CheckFull((String)pr.getObject());
	                        out.writeObject(fullroom);
	                        break;
	                    case "getEId":
	                        info = (Protocol)pr.getObject();
	                        id = info.getCommand();
	                    	String eid = roomDataSet.getEId((String)info.getObject(), id);
	                        out.writeObject(eid);
	                        break;
	                    case "BattleSkill":
	                        info = (Protocol)pr.getObject();
	                        id = info.getCommand();
	                    	skillDataSet.BattleSkill(id, (String)info.getObject());
	                        break;
	                    case "bstate":
	                    	roomDataSet.bstate((String)pr.getObject());
	                        break;
	                    case "getTurn":
	                    	int gt = roomDataSet.getTurn((String)pr.getObject());
	                        out.writeObject(gt);
	                        break;
	                    case "setTurn":
	                        info = (Protocol)pr.getObject();
	                        String sroomnum = info.getCommand();
	                    	roomDataSet.setTurn(sroomnum, (int)info.getObject());
	                        break;
	                    case "isSkillOverlap":
	                        out.writeObject(skillDataSet.isSkillOverlap((String)pr.getObject()));
	                        break;
	                    case "SetUskill":
	                        out.writeObject(skillDataSet.SetUskill((String)pr.getObject()));
	                        break;
	                    case "set_attack":
	                        info = (Protocol)pr.getObject();
	                        id = info.getCommand();
	                        roomDataSet.set_attack(id, (int)info.getObject());
	                    	break;
	                    case "get_attack":
	                        out.writeObject(roomDataSet.get_attack((String)pr.getObject()));
	                        break;
	                    case "set_defence":
	                        info = (Protocol)pr.getObject();
	                        id = info.getCommand();
	                        roomDataSet.set_defence(id, (int)info.getObject());
	                    	break;
	                    case "get_defence":
	                        out.writeObject(roomDataSet.get_defence((String)pr.getObject()));
	                        break;
	                    case "sendMessage":
	                        info = (Protocol)pr.getObject();
	                        id = info.getCommand();
	                        roomDataSet.sendMessage(id, (String)info.getObject());
	                    	break;
	                    case "getMessage":
	                        out.writeObject(roomDataSet.getMessage((String)pr.getObject()));
	                        break;
	                    default:
	                        out.writeObject("INVALID_OPERATION");
	                        break;
	                }
	            }
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	            System.out.println(id + "님이 퇴장하셨습니다.");
				removeClient(this, id);
	        } finally {
	            try {
	                clientSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
		
		//private ResultPacket doIt(RequestData o) { … return aResultPacket;}

	}
}
