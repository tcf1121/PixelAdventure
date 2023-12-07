package GUI.BattleScreen;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;


import Back.Datarelated.Ability;
import GUI.BattleScreen.Popup.ChattingPopup;
import GUI.BattleScreen.Popup.battleresultPopup;
import GUI.MainScreen.WaitForm;
import Back.Datarelated.Skill;
import Back.Server.MySocketClient;

public class BattleForm extends Thread{
    /**
	 * 
	 */
	JScrollPane scrollPane;
    ImageIcon icon;
    private JFrame Jf;
	private JButton[] btnSkill;
	private JButton[] btnSkillex;
	private JButton btnTurnend;
	private JButton btnback;
	private JLabel lblCharater;
	private JLabel lbleCharater;
	private JLabel lblbg;
	private JLabel lblOnedef;
	private JLabel lbleOnedef;
	private JLabel lbltime;
	private JLabel lblnickname;
	private JLabel lblenickname;
	private JLabel lblturn;
	private JLabel lbleturn;
	private WaitForm owner;
	private String id;
	private String eid;
    private Ability Now_Ability = new Ability("" , 0, 0, 0, 0);
    private ImageIcon Charicon;
    private ImageIcon eCharicon;
    private JProgressBar Hpbar;
    private JProgressBar eHpbar;
    private JLabel Mpbar;
    private int max_hp, now_hp;
    private int max_ehp, now_ehp;
    private int max_mp = 0;
    private int now_mp = 0;
    private int magic_str=0;
    private int magic_def=0;
    private int onetime_def = 0;
    private int eonetime_def = 0;
	MySocketClient Socket;
	public String roomnum;
	ArrayList<Skill> arrayS = new ArrayList<Skill>();
	public int turn;
	static int count;
	JPanel bg;
	
	public BattleForm(WaitForm owner, String roomnum, int turn) throws ClassNotFoundException, IOException {
		this.roomnum = roomnum;
        this.owner = owner;
        this.turn = turn;
        id = owner.getId();
        try {
        	Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        eid = Socket.getEId(roomnum, id);
        Socket.startchat(id);
        Socket.bstate(id);
        Now_Ability = Socket.getNAbility(id);
        img_class(Now_Ability.getcl());
        img_class(Now_Ability.getcl());
        eimg_class(Socket.getNAbility(eid).getcl());
        max_hp = Now_Ability.getHp();
        now_hp = max_hp;
        max_ehp = Socket.getNAbility(eid).getHp();
        now_ehp = max_hp;
		btnSkill = new JButton[5];
		btnSkillex = new JButton[5];
		for(int i=0; i<2;i++) {
			Skill s =  new Skill("", "", "", "", 0, 0);
			s = Socket.SetUskill(id);
			arrayS.add(s);
		}


		icon = new ImageIcon(BattleForm.class.getResource("/image/background/battlebg.png"));
		Jf = new JFrame();
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        bg = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        bg.setLayout(null);
		init();

		bg.add(btnTurnend);
		bg.add(btnback);
		bg.add(Hpbar);
		bg.add(eHpbar);
		bg.add(Mpbar);


		bg.add(lblCharater);
		bg.add(lbleCharater);
		bg.add(lblOnedef);
		bg.add(lbleOnedef);
		bg.add(lblturn);
		bg.add(lbleturn);
		bg.add(lbltime);
		bg.add(lblnickname);
		bg.add(lblenickname);
		bg.add(lblbg);
        scrollPane = new JScrollPane(bg);

        Jf.setContentPane(scrollPane);
        
        addListeners();
		showFrame();
		ChattingPopup chat = new ChattingPopup(owner, roomnum);
		chat.start();
	}
	//위치 설정
	private void init() {
		ImageIcon shieldicon = new ImageIcon(BattleForm.class.getResource("/image/ui/shield.png"));
		ImageIcon turnicon = new ImageIcon(BattleForm.class.getResource("/image/ui/turnicon.png"));

		
		lblCharater = new JLabel(Charicon);
		lblCharater.setBounds(63, 125, 200, 200);
		lbleCharater = new JLabel(eCharicon);
		lbleCharater.setBounds(518, 125, 200, 200);
		lblbg = new JLabel(new ImageIcon(BattleForm.class.getResource("/image/background/battlebg.png")));
		lblbg.setBounds(0, 0, 786, 560);
		
		lblnickname = new JLabel(id);
		lblnickname.setHorizontalAlignment(JLabel.CENTER);
		lblnickname.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		lblnickname.setForeground(new Color(255, 255, 255));
		lblnickname.setBounds(239, 38, 139, 25);
		
		lblenickname = new JLabel(eid);
		lblenickname.setHorizontalAlignment(JLabel.CENTER);
		lblenickname.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		lblenickname.setForeground(new Color(255, 255, 255));
		lblenickname.setBounds(419, 38, 129, 25);
		
		lblturn = new JLabel(turnicon);
		lblturn.setBounds(112, 107, 46, 46);
		lbleturn = new JLabel(turnicon);
		lbleturn.setBounds(647, 107, 46, 46);
		if(turn ==0) {lbleturn.setVisible(false);}
		else {lblturn.setVisible(false);}
		
		
		btnback = new JButton();
        btnback.setBounds(27, 3, 38, 50);
        btnback.setBorderPainted(false);
		btnback.setContentAreaFilled(false);
		
		btnTurnend = new JButton();
		btnTurnend.setBounds(15, 476, 122, 69);
		btnTurnend.setBorderPainted(false);
		btnTurnend.setContentAreaFilled(false);
		
		Hpbar = new JProgressBar(0, max_hp);
		Hpbar.setStringPainted(true);
		Hpbar.setValue(now_hp);
		Hpbar.setForeground(Color.red);
		Hpbar.setBackground(Color.black);
		Hpbar.setString(now_hp+"/"+max_hp);
		Hpbar.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		Hpbar.setBounds(117, 3, 276, 25);
		
		eHpbar = new JProgressBar(0, max_ehp);
		eHpbar.setStringPainted(true);
		eHpbar.setValue(max_ehp - now_ehp);
		eHpbar.setForeground(Color.black);
		eHpbar.setBackground(Color.red);
		eHpbar.setString(now_ehp+"/"+max_ehp);
		eHpbar.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		eHpbar.setBounds(394, 3, 276, 25);
		
		
		Mpbar = new JLabel(Integer.toString(now_mp));
		Mpbar.setHorizontalAlignment(JLabel.CENTER);
		Mpbar.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		Mpbar.setForeground(new Color(255, 255, 255));
		Mpbar.setBounds(58, 426, 38, 25);
		
		lbltime = new JLabel(Integer.toString(count));
		lbltime.setHorizontalAlignment(JLabel.CENTER);
		lbltime.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		lbltime.setForeground(new Color(255, 255, 255));
		lbltime.setBounds(372, 90, 50, 25);
		
        lblOnedef = new JLabel(shieldicon);
        lblOnedef.setForeground(Color.white);
        lblOnedef.setBounds(230, 200, 50, 50);
        lblOnedef.setHorizontalTextPosition(JLabel.CENTER);
        lblOnedef.setVerticalTextPosition(JLabel.CENTER);
        lblOnedef.setText(String.valueOf(onetime_def));
        lblOnedef.setVisible(false);
        
        lbleOnedef = new JLabel(shieldicon);
        lbleOnedef.setForeground(Color.white);
        lbleOnedef.setBounds(498, 200, 50, 50);
        lbleOnedef.setHorizontalTextPosition(JLabel.CENTER);
        lbleOnedef.setVerticalTextPosition(JLabel.CENTER);
        lbleOnedef.setText(String.valueOf(eonetime_def));
        lbleOnedef.setVisible(false);
        
        for(int i=0;i<5;i++) {
			btnSkill[i] = new JButton();
			btnSkill[i].setBorderPainted(false);
			btnSkill[i].setContentAreaFilled(false);
			btnSkill[i].setBounds(159 +(i*123), 420, 80, 120);
			btnSkill[i].setVisible(false);
			btnSkillex[i] = new JButton();
			btnSkillex[i].setBorderPainted(false);
			btnSkillex[i].setContentAreaFilled(false);
			btnSkillex[i].setBounds(250 +(i*123), 418, 15, 15);
			btnSkillex[i].setVisible(false);
			bg.add(btnSkill[i]);
			bg.add(btnSkillex[i]);
        }

	}
	
	private void img_class(String class_no) {
		if(class_no.equals("전사")) {
			Charicon = new ImageIcon(BattleForm.class.getResource("/image/character/warrior_stand.gif"));
		}
		else if(class_no.equals("마법사")) {
			Charicon = new ImageIcon(BattleForm.class.getResource("/image/character/wizard_stand.gif"));
		}
		else if(class_no.equals("도적")) {
			Charicon = new ImageIcon(BattleForm.class.getResource("/image/character/rouge_stand.gif"));
		}
	}
	private void eimg_class(String class_no) {
		if(class_no.equals("전사")) {
			eCharicon = new ImageIcon(BattleForm.class.getResource("/image/character/warrior_battle.gif"));
		}
		else if(class_no.equals("마법사")) {
			eCharicon = new ImageIcon(BattleForm.class.getResource("/image/character/wizard_battle.gif"));
		}
		else if(class_no.equals("도적")) {
			eCharicon = new ImageIcon(BattleForm.class.getResource("/image/character/rouge_battle.gif"));
		}
	}
	
	//占쏙옙튼 占싱븝옙트 占쌓쇽옙
	private void addListeners() {
        btnTurnend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					Socket.setTurn(roomnum, turn==1?0:1);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

       
        Jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	int choice = JOptionPane.showConfirmDialog(
                		Jf,
                        "게임을 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
            	if (choice == JOptionPane.YES_OPTION) {
            		try {
						Socket.logOut(Socket.getUser(id));
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		Socket.endServer();
            		System.exit(0);
                }
                
            }
        });
    }
	public void attack(int damage) {
		if(eonetime_def>damage) {
			eonetime_def -= damage;
			lbleOnedef.setText(String.valueOf(eonetime_def));
		}else if(eonetime_def == damage) {
			eonetime_def = 0;
			lbleOnedef.setVisible(false);
		}else {
			damage -= eonetime_def;
			eonetime_def = 0;
			lbleOnedef.setVisible(false);
			now_ehp -= damage;
			eHpbar.setValue(max_ehp - now_ehp);
			eHpbar.setString(now_ehp+"/"+max_ehp);
		}
	}
	public void be_attack(int damage) {
		if(onetime_def>damage) {
			onetime_def -= damage;
			lblOnedef.setText(String.valueOf(onetime_def));
		}else if(onetime_def == damage) {
			onetime_def = 0;
			lblOnedef.setVisible(false);
		}else {
			damage -= onetime_def;
			onetime_def = 0;
			lblOnedef.setVisible(false);
			now_hp -= damage;
			Hpbar.setValue(now_hp);
			Hpbar.setString(now_hp+"/"+max_hp);
		}
	}
	public void Eonedef(int defence) {
		eonetime_def = defence;
		if(eonetime_def > 0) {
			lbleOnedef.setVisible(true);
			lbleOnedef.setText(String.valueOf(eonetime_def));
		}
	}
    private void showFrame() {
        Jf.setTitle("픽셀 어드벤쳐");//타이틀
        Jf.setSize(800,600);//프레임의 크기
        Jf.setLocation(owner.getLocation());
        Jf.setDefaultCloseOperation(2);
        Jf.setResizable(false);//창의 크기를 변경하지 못하게
        Jf.setVisible(true);
    }
	public void run(){
		
		count = 30;
		Skill s =  new Skill("", "", "", "", 0, 0);
		int nowturn;
		int checkturn;
		if(turn == 1) {checkturn = 0;}
		else {checkturn = 1;}
		while (true) {
			if(now_hp < 1 || now_ehp<1) {
				break;
			}
			try {
				Thread.sleep(1000);
				nowturn =Socket.getTurn(roomnum);
				if(nowturn== turn) {
					if(checkturn != nowturn) {
						checkturn = nowturn;
						btnTurnend.setVisible(true);
						lblturn.setVisible(true);
						lbleturn.setVisible(false);
						lblOnedef.setVisible(false);
						count = 30;
						onetime_def = 0;
						magic_def = 0;
						magic_str = 0;
						if(max_mp < Now_Ability.getMp()) {
							max_mp += Now_Ability.getMp()/10;
							now_mp = max_mp;
							Mpbar.setText(String.valueOf(now_mp));
						}
						
						if(arrayS.size()<5) {
							if(Socket.isSkillOverlap(id)) {
								s = Socket.SetUskill(id);
								arrayS.add(s);
							}
						}else if(arrayS.size()==0) {
							if(!Socket.isSkillOverlap(id)) {
								s = Socket.infoSkill("01");
								arrayS.add(s);
							}	
						}
						for(int i=0;i<arrayS.size();i++) {
							ImageIcon skillicon = new ImageIcon(BattleForm.class.getResource("/image/skill/"+arrayS.get(i).getId()+".png"));
							btnSkill[i].setIcon(skillicon);
							btnSkill[i].setVisible(true);
							btnSkillex[i].setVisible(true);
							int btn = i;
							btnSkill[btn].addActionListener(new ActionListener() {
				                @Override
				                public void actionPerformed(ActionEvent e) {
				                	if(now_mp >= arrayS.get(btn).getUmana()) {
				                    		if(arrayS.get(btn).getType().equals("S_attack") || arrayS.get(btn).getType().equals("W_attack")) {
				                                JOptionPane.showMessageDialog(
				                                        Jf,
				                                        ""+arrayS.get(btn).getExplan(),"효과",JOptionPane.PLAIN_MESSAGE
				                                        );
				                                int attack = arrayS.get(btn).getEffect()+Now_Ability.getStr()+magic_str;
				                                if(arrayS.get(btn).getId().equals("13")) {
				                                	attack = Now_Ability.getDef()+magic_def;
				                                }
				                                attack(attack);
				                                try {
													Socket.set_attack(id, attack);
												} catch (ClassNotFoundException | IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
				                            }
				                        	else if(arrayS.get(btn).getType().equals("Defense")) {
				                                JOptionPane.showMessageDialog(
				                                        Jf,
				                                        ""+arrayS.get(btn).getExplan(),"효과",JOptionPane.PLAIN_MESSAGE
				                                        );
				                                onetime_def += arrayS.get(btn).getEffect()+ Now_Ability.getDef() + magic_def;
				                                lblOnedef.setText(String.valueOf(onetime_def));
				                                lblOnedef.setVisible(true);
				                                try {
													Socket.set_defence(id, onetime_def);
												} catch (ClassNotFoundException | IOException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
				                            }
				                        	else if(arrayS.get(btn).getType().equals("Magic")) {
				                                JOptionPane.showMessageDialog(
				                                        Jf,
				                                        ""+arrayS.get(btn).getExplan(),"마법",JOptionPane.PLAIN_MESSAGE
				                                        );  
				                                	if(arrayS.get(btn).getId().equals("15")) {
				                                		magic_def = 20;
				                                	}
				                                	else if(arrayS.get(btn).getId().equals("03")) {
				                                		now_mp += 6;
				                                	}
				                                	else if(arrayS.get(btn).getId().equals("35")) {
				                                		magic_str = 20;
				                                	}
				                            }
				                		now_mp -= arrayS.get(btn).getUmana();
				                		Mpbar.setText(Integer.toString(now_mp));
				                		btnSkill[btn].setVisible(false);
				                		btnSkillex[btn].setVisible(false);
				                		arrayS.set(btn, null);
				                	}
				                	else {
				                		JOptionPane.showMessageDialog(
				                                Jf,
				                                "마나가 부족합니다.","마나 부족",JOptionPane.PLAIN_MESSAGE
				                                );  
				                	}
				                	
				                }
				                	
				        	});
					        btnSkillex[btn].addActionListener(new ActionListener() {
					                @Override
					                public void actionPerformed(ActionEvent e) {
					                    JOptionPane.showMessageDialog(
					                            Jf,
					                            " "+ arrayS.get(btn).getName() + "\n" +
					                            "마나 비용 :" + arrayS.get(btn).getUmana()+ "\n" +
					                            "스킬 설명 :" + arrayS.get(btn).getExplan() + "\n",
					                            "스킬 정보",JOptionPane.PLAIN_MESSAGE
					                          );             
					               }
					        });
						}
					}
					lbltime.setText(String.valueOf(count));
					count--;
					if(count <1) {
						Socket.setTurn(roomnum, turn==1?0:1);
						
					}
					
				}else {
					if(checkturn != nowturn) {
						checkturn = nowturn;
						lblturn.setVisible(false);
						lbleturn.setVisible(true);
						count = 30;
						eonetime_def = 0;
						lbleOnedef.setVisible(false);
						btnTurnend.setVisible(false);
						for(int i=0;i<5;i++) {
							btnSkill[i].setVisible(false);
							btnSkillex[i].setVisible(false);
						}
						while(arrayS.remove(null)) {};
					}

					lbltime.setText(String.valueOf(count));
					count--;
					int beattack = Socket.get_attack(eid);
					int onedef = Socket.get_defence(eid);
					if(beattack>0) {
						be_attack(beattack);
					}
					if(onedef>0) {
						Eonedef(onedef);
					}
					if(count<1) {
						Socket.setTurn(roomnum, turn==1?0:1);
					}
				}
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			Socket.deleteRoom(roomnum);
			Socket.deletechat(id);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(now_hp<=0) {	new battleresultPopup(this, 0);	}
		else {	new battleresultPopup(this, 1);	}
		
	}
	public JFrame getjf() {
		return this.Jf;
	}
	public WaitForm getowner() {
		return this.owner;
	}
}
