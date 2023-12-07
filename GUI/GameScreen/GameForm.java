package GUI.GameScreen;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import Back.Datarelated.Ability;
import Back.Datarelated.Dungeon;
import GUI.MainScreen.WaitForm;
import Back.Datarelated.Monster;
import Back.Datarelated.Skill;
import Back.Datarelated.Store;
import Back.Server.MySocketClient;
import GUI.GameScreen.Popup.AbilityPopup;
import GUI.GameScreen.Popup.OverPopup;
import GUI.GameScreen.Popup.RewardAPopup;
import GUI.GameScreen.Popup.RewardPopup;
import GUI.GameScreen.Popup.StorePopup;

public class GameForm extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    ImageIcon mapbg = new ImageIcon();
    ImageIcon moveicon;
    ImageIcon battleicon;
	public int skillnum = 3;
	private Skill[] Skill;
	private JButton[] btnSkill;
	private JButton[] btnSkillex;
	private JButton btnTurnend;
	private JButton btnLookAb;
	private JLabel lblCharater;
	private JLabel lblOnedef;
	private Random random = new Random();
	private int mon_num = random.nextInt(3)+1;
	private JLabel[] lblMonster;
	private JLabel[] lblattack;
	private WaitForm owner;
	private String id;
    private Ability Now_Ability = new Ability("" , 0, 0, 0, 0);
    private ImageIcon Charicon;
    private JProgressBar Hpbar;
    private JProgressBar Mpbar;
    private int max_hp, now_hp;
    private int max_mp, now_mp;
    private int monsize;
    private int s_num;
    private Store store;
    private Monster[] Monster;
    private String[] s_mon;
    private String[] w_mon = {"전체 공격"};
    private JTextArea BattleLog;
    private JScrollPane scrollLog;
    private int magic_str=0;
    private int magic_def=0;
    private int onetime_def = 0;
    private JLabel dungeonbg;
    private JLabel move;
    private JLabel battle;
	private JButton btnleft;
	private JButton btndown;
	private JButton btnright;
	private JButton btnPotion;
	private JButton btnStore;
	private JLabel lblPcount;
	private JLabel lblGcount;
	private JLabel lblfloor;
	public int floor;
	public int gold;
	public int Maxpotion = 3;
	public int Nowpotion;
	public Dungeon dungeon;
	public int difficulty;
	public int location;
	public int clear;
	public char maptype;
	private MySocketClient Socket;
	
	public int exp;
	public GameForm(WaitForm owner, int floor, int location, int Hp, int gold, int potion, int exp) throws ClassNotFoundException, IOException {
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        this.owner = owner;
        this.exp = exp;
        id = owner.getId();
        this.floor = floor;
        this.location = location;
        this.dungeon = Socket.getDungeon(id, location);
        this.difficulty = dungeon.getDifficulty();
        this.clear = dungeon.getClear();
        this.maptype = dungeon.getMaptype();
        this.store = Socket.getStore(id);      
		if(floor >2) {
			skillnum = 4;
		}
		if(store.getSkill_card()==1) {
			skillnum = 5;
		}
		Skill = Socket.randUskill(id, skillnum);
		if(store.getPotion()==1) {
			Maxpotion = 4;
		}
		monsize = 100;
		if (clear == 1) {
			mon_num = 0;
		}
		else {
			if(difficulty > 5) {
				mon_num = random.nextInt(2)+1;
				monsize = 150;
			}if(maptype == 'b') {
				mon_num = 1;
				monsize = 400;}
		}
		this.gold = gold;
        Now_Ability = Socket.getNAbility(id);
        max_hp = Now_Ability.getHp();
        now_hp = Hp;
        max_mp = Now_Ability.getMp();
        now_mp = max_mp;
        Nowpotion = potion;
		s_num = floor; 
		btnSkill = new JButton[skillnum];
		btnSkillex = new JButton[skillnum];
		lblMonster = new JLabel[mon_num];
		lblattack = new JLabel[mon_num];
		Monster = new Monster[mon_num];
		s_mon = new String[mon_num];
		icon = new ImageIcon(GameForm.class.getResource("/image/background/gamebg.png"));
		moveicon = new ImageIcon(GameForm.class.getResource("/image/ui/move.png"));
		battleicon = new ImageIcon(GameForm.class.getResource("/image/ui/battle.png"));
		dungeonsetting(maptype, location); // 위치와 맵 타입에 따라 설정하기
		dungeonbg = new JLabel(mapbg);
		dungeonbg.setBounds(0, 0, 780, 352);
		move = new JLabel(moveicon);
		move.setBounds(0, 354, 780, 204);
		battle = new JLabel(battleicon);
		battle.setBounds(0, 354, 780, 204);
        img_class(Now_Ability.getcl());
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        JPanel bg = new JPanel() {
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

		if(location != 0) {
			move.add(btnleft);
		}
		if(maptype == 'd') {
			move.add(btndown);
		}
		if(location != 4) {
			move.add(btnright);	
		}
			
		bg.add(move);
		move.setVisible(false);
        
		for(int i=0;i<skillnum;i++) {
			battle.add(btnSkill[i]);
			battle.add(btnSkillex[i]);
		}
		battle.add(btnTurnend);
		battle.add(btnLookAb);
		battle.add(Hpbar);
		battle.add(Mpbar);
		battle.add(btnPotion);
		battle.add(lblGcount); battle.add(lblPcount); battle.add(lblfloor);
		bg.add(battle);
		
		if(clear ==1) {
			move.setVisible(true);
			battle.setVisible(false);
		}
		dungeonbg.add(scrollLog);
		dungeonbg.add(lblCharater);
		dungeonbg.add(lblOnedef);
		for(int i=0;i<mon_num;i++) {
			dungeonbg.add(lblMonster[i]);
			dungeonbg.add(lblattack[i]);
		}
		if(floor ==4 && maptype == 's') {
			dungeonbg.add(btnStore);
		}
				
		bg.add(dungeonbg);
        scrollPane = new JScrollPane(bg);

        setContentPane(scrollPane);
        
        addListeners();
		showFrame();
	}
	//위치 설정
	private void init() {
		Font font = new Font("SansSerif", 1, 20);
        Color WColor = new Color(255, 255, 255);
        Color BColor = new Color(0, 0, 0,122);
		ImageIcon shieldicon = new ImageIcon(GameForm.class.getResource("/image/ui/shield.png"));
		ImageIcon attackicon = new ImageIcon(GameForm.class.getResource("/image/ui/attack.png"));
		ImageIcon lefticon = new ImageIcon(GameForm.class.getResource("/image/ui/left.png"));
		ImageIcon righticon = new ImageIcon(GameForm.class.getResource("/image/ui/right.png"));
		ImageIcon downicon = new ImageIcon(GameForm.class.getResource("/image/ui/down.png"));
		ImageIcon storeicon = new ImageIcon(GameForm.class.getResource("/image/ui/Store.png"));
		
		for(int i=0;i<skillnum;i++) {
			ImageIcon skillicon = new ImageIcon(GameForm.class.getResource("/image/skill/"+Skill[i].getId()+".png"));
			btnSkill[i] = new JButton(skillicon);
			btnSkill[i].setBorderPainted(false);
			btnSkill[i].setContentAreaFilled(false);
			btnSkill[i].setBounds(155 +(i*123), 65, 80, 120);
			btnSkillex[i] = new JButton();
			btnSkillex[i].setBorderPainted(false);
			btnSkillex[i].setContentAreaFilled(false);
			btnSkillex[i].setBounds(245 +(i*123), 63, 15, 15);
		}
		btnPotion = new JButton();
		btnPotion.setBounds(28, 68, 80, 80);
		btnPotion.setBorderPainted(false);
		btnPotion.setContentAreaFilled(false);
		
		lblfloor = new JLabel(String.valueOf(this.floor)+"층");
		lblfloor.setFont(font);
		lblfloor.setForeground(WColor);
		lblfloor.setBounds(673, 20, 60, 20);
		
		lblGcount = new JLabel(String.valueOf(gold)+"G");
		lblGcount.setFont(font);
		lblGcount.setForeground(WColor);
		lblGcount.setBounds(543, 20, 60, 20);
		
		lblPcount = new JLabel(String.valueOf(Nowpotion)+"/"+String.valueOf(Maxpotion));
		lblPcount.setFont(font);
		lblPcount.setForeground(WColor);
		lblPcount.setBounds(53, 166, 60, 20);
		
		lblCharater = new JLabel(Charicon);
		lblCharater.setBounds(63, 125, 200, 200);
		
		for(int i=0;i<mon_num;i++) {
			ImageIcon Monicon = new ImageIcon(GameForm.class.getResource("/image/monster/"+difficulty +".png"));
			Monster[i] = new Monster(0, 0, 0);
			lblMonster[i] = new JLabel(Monicon);
			lblMonster[i].setBounds(420+(i*(10+monsize)), 307-monsize,
					monsize, monsize);
			Monster[i].addMon(s_num);
	        lblattack[i] = new JLabel(attackicon);
	        lblattack[i].setForeground(Color.white);
	        lblattack[i].setBounds(445+(i*(10+monsize)), 260-monsize, 50, 50);
	        lblattack[i].setHorizontalTextPosition(JLabel.CENTER);
	        lblattack[i].setVerticalTextPosition(JLabel.CENTER);
	        lblattack[i].setText(String.valueOf(Monster[i].getStr()));
			if(difficulty ==10) {
				lblMonster[i].setBounds(428, -52, monsize, monsize);
			}
			s_mon[i] = Integer.toString(i+1);

		}

		
		btnLookAb = new JButton();
        btnLookAb.setBounds(238, 16, 30, 30);
        btnLookAb.setBorderPainted(false);
		btnLookAb.setContentAreaFilled(false);
		
		btnTurnend = new JButton();
		btnTurnend.setBounds(300, 3, 180, 50);
		btnTurnend.setBorderPainted(false);
		btnTurnend.setContentAreaFilled(false);
		
		Hpbar = new JProgressBar(0, max_hp);
		Hpbar.setStringPainted(true);
		Hpbar.setValue(now_hp);
		Hpbar.setForeground(Color.red);
		Hpbar.setBackground(Color.black);
		Hpbar.setString(now_hp+"/"+max_hp);
		Hpbar.setBounds(30, 3, 200, 25);
		
		Mpbar = new JProgressBar(0, max_mp);
		Mpbar.setStringPainted(true);
		Mpbar.setValue(now_mp);
		Mpbar.setForeground(Color.blue);
		Mpbar.setBackground(Color.black);
		Mpbar.setString(now_mp+"/"+max_mp);
		Mpbar.setBounds(30, 28, 200, 25);
		
		BattleLog = new JTextArea(); 
		BattleLog.setBackground(BColor);
		BattleLog.setForeground(WColor);
		BattleLog.setEditable(false);
        TitledBorder border = new TitledBorder(new LineBorder(WColor, 2), "전투 기록");
        border.setTitleColor(WColor);
        scrollLog = new JScrollPane(BattleLog);
        scrollLog.setOpaque(false);
        scrollLog.setBorder(border);
        scrollLog.setBounds(10, 10, 280, 100);
        
        lblOnedef = new JLabel(shieldicon);
        lblOnedef.setForeground(Color.white);
        lblOnedef.setBounds(230, 200, 50, 50);
        lblOnedef.setHorizontalTextPosition(JLabel.CENTER);
        lblOnedef.setVerticalTextPosition(JLabel.CENTER);
        lblOnedef.setText(String.valueOf(onetime_def));
        lblOnedef.setVisible(false);
        if(floor == 4 && maptype == 's') {
            btnStore = new JButton(storeicon);        
            btnStore.setBorderPainted(false);
            btnStore.setContentAreaFilled(false);
            btnStore.setBounds(500, 53, 192, 253);
        }        
        if(location != 0) {
            btnleft = new JButton(lefticon);
            btnleft.setBorderPainted(false);
            btnleft.setContentAreaFilled(false);
            btnleft.setBounds(154, 53, 122, 122);
        }
        if(maptype == 'd') {
            btndown = new JButton(downicon);
            btndown.setBorderPainted(false);
            btndown.setContentAreaFilled(false);
            btndown.setBounds(328, 53, 122, 122);
        }

        if(location != 4) {
            btnright = new JButton(righticon);
            btnright.setBorderPainted(false);
            btnright.setContentAreaFilled(false);
            btnright.setBounds(503, 53, 122, 122);
        }
	}
	
	private void img_class(String class_no) {
		if(class_no.equals("전사")) {
			Charicon = new ImageIcon(GameForm.class.getResource("/image/character/warrior_stand.gif"));
		}
		else if(class_no.equals("마법사")) {
			Charicon = new ImageIcon(GameForm.class.getResource("/image/character/wizard_stand.gif"));
		}
		else if(class_no.equals("도적")) {
			Charicon = new ImageIcon(GameForm.class.getResource("/image/character/rouge_stand.gif"));
		}
	}
	
	//占쏙옙튼 占싱븝옙트 占쌓쇽옙
	private void addListeners() {
    	btnTurnend.addActionListener(new ActionListener() {
    		private int save_hp;
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        GameForm.this,
                        "턴 종료");   
                
                if(onetime_def >0){
                	save_hp = now_hp;
                	now_hp += onetime_def;
                }
                for(int i=0;i<s_mon.length;i++) {
                	now_hp -= Monster[monster_death(s_mon[i])].getStr();
                	addBattleLog(StrLog(monster_name(difficulty),
                			owner.getNickname(), Monster[monster_death(s_mon[i])].getStr())); 
                }
                if(onetime_def >0){
                	if(now_hp > save_hp){
                		now_hp = save_hp;
                	}
                }
                lblOnedef.setVisible(false);
                onetime_def = 0;
                magic_str = 0;
                magic_def = 0;
                now_mp = max_mp;
                Hpbar.setValue(now_hp);
        		Hpbar.setString(now_hp+"/"+max_hp);
            	Mpbar.setValue(now_mp);
        		Mpbar.setString(now_mp+"/"+max_mp);
        		
        		try {
					Skill = Socket.randUskill(id, skillnum);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
        		for(int i=0;i<skillnum;i++) {		
        			ImageIcon skillicon = new ImageIcon(GameForm.class.getResource("/image/skill/"+Skill[i].getId()+".png"));
        			btnSkill[i].setVisible(true);
        			btnSkill[i].setIcon(skillicon);
        			btnSkillex[i].setVisible(true);
        		}
        		if(now_hp<1) {
        			new OverPopup(owner, exp, 0/*보스 클리어 실패*/);
        		}
            }
            
        });
        btnLookAb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Ability CAbility = null;
				try {
					CAbility = Socket.getNAbility(id);
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
            	new AbilityPopup(owner, CAbility);
            	
            }
        });
        btnPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Nowpotion == 0) {
                	JOptionPane.showMessageDialog(
                            GameForm.this,
                            "포션을 전부 사용했습니다.","포션 사용",JOptionPane.PLAIN_MESSAGE
                            ); 
            	}
            	else {
            		Nowpotion--;
                	JOptionPane.showMessageDialog(
                            GameForm.this,
                            (max_hp/2)+"만큼 HP를 회복합니다.","포션 사용",JOptionPane.PLAIN_MESSAGE
                    );  
                    now_hp += (max_hp/2);
                    if(now_hp >max_hp) {
                    	now_hp = max_hp;
                    }
                    Hpbar.setValue(now_hp);
                    Hpbar.setString(now_hp+"/"+max_hp);
                    lblPcount.setText(String.valueOf(Nowpotion)+"/"+String.valueOf(Maxpotion));	
            	}           	
            }
            
        });
        if(floor == 4 && maptype == 's') {
        	btnStore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	try {
						new StorePopup(GameForm.this, id, gold);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
                }
            });
        }
        
        if(location != 0) {
            btnleft.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	dispose();
                	try {
						new GameForm(owner, floor, location-1, now_hp, gold, Nowpotion, exp);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
                }
            });
        }
        if(maptype == 'd') {
        	btndown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {              	
                	try {
                		Socket.DeleteMap(id);
						Socket.setMap(id, floor+1);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
                	dispose();
                	try {
						new GameForm(owner, floor+1, Socket.getStartpoint(id), now_hp, gold, Maxpotion, exp);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
                }
        	});	
        }
        if(location != 4) {
        	btnright.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	dispose();
                	try {
						new GameForm(owner, floor, location+1, now_hp, gold, Nowpotion, exp);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
                }
        	});
        }
        

        for(int btnsk=0;btnsk<skillnum;btnsk++) {
        	int btn = btnsk;
        	btnSkill[btn].addActionListener(new ActionListener() {
    		private int death_num;
    		private int num;
    		private String death_mon;
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(now_mp >= Skill[btn].getUmana()) {
                		if(s_mon.length >0) {
                    		if(Skill[btn].getType().equals("S_attack")) {
                        		num = JOptionPane.showOptionDialog(null, "단일 공격", "공격", JOptionPane.YES_NO_CANCEL_OPTION,
                            			JOptionPane.QUESTION_MESSAGE, null, s_mon, null);
                        		num = monster_death(s_mon[num]);
                        		if(Skill[btn].getId().equals("13")) {
                        			try {
										Monster[num].damage(Socket.effectSkill(Skill[btn], Now_Ability.getDef()+magic_def));
									} catch (ClassNotFoundException | IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
                                	addBattleLog( StrLog(owner.getNickname(),monster_name(difficulty),
                                			Now_Ability.getDef()+magic_def+Skill[btn].getEffect())); 
                        		}
                        		else {
                        			try {
										Monster[num].damage(Socket.effectSkill(Skill[btn], Now_Ability.getStr()+magic_str));
									} catch (ClassNotFoundException | IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
                        			addBattleLog( StrLog(owner.getNickname(),monster_name(difficulty),
                        					Now_Ability.getStr()+magic_str+Skill[btn].getEffect())); 
                        		}
                            }
                        	else if(Skill[btn].getType().equals("W_attack")) {
                        		JOptionPane.showOptionDialog(null, "전체 공격", "공격", JOptionPane.YES_NO_CANCEL_OPTION,
                            			JOptionPane.QUESTION_MESSAGE, null, w_mon, null);
                        		for(int i =0;i<s_mon.length;i++) {
                        			num = monster_death(s_mon[i]);
                            		try {
										Monster[num].damage(Socket.effectSkill(Skill[btn], Now_Ability.getStr()+magic_str));
									} catch (ClassNotFoundException | IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
                            		addBattleLog( StrLog(owner.getNickname(),monster_name(difficulty),
                        					Now_Ability.getStr()+magic_str+Skill[btn].getEffect())); 
                        			}
                            	}
                        	else if(Skill[btn].getType().equals("Defense")) {
                                JOptionPane.showMessageDialog(
                                        GameForm.this,
                                        ""+Skill[btn].getExplan(),"효과",JOptionPane.PLAIN_MESSAGE
                                        );
                                onetime_def += Skill[btn].getEffect()+ Now_Ability.getDef() + magic_def;
                                lblOnedef.setText(String.valueOf(onetime_def));
                                lblOnedef.setVisible(true);
                                addBattleLog(owner.getNickname()+"(이)가"+ onetime_def+ "만큼 데미지를 막습니다.");
                            	}
                        	else if(Skill[btn].getType().equals("Magic")) {
                                JOptionPane.showMessageDialog(
                                        GameForm.this,
                                        ""+Skill[btn].getExplan(),"마법",JOptionPane.PLAIN_MESSAGE
                                        );  
                                	if(Skill[btn].getId().equals("15")) {
                                		magic_def = 20;
                                	}
                                	else if(Skill[btn].getId().equals("03")) {
                                		now_mp += 6;
                                        Mpbar.setValue(now_hp);
                                        Mpbar.setString(now_mp+"/"+max_mp);
                                	}
                                	else if(Skill[btn].getId().equals("35")) {
                                		magic_str = 20;
                                	}
                                	addBattleLog(owner.getNickname()+"(이)가"+ Skill[btn].getName() +" 마법을 사용했습니다.");
                            	}
                    		for(int i=0;i<s_mon.length;i++) {
                        		death_num = monster_death(s_mon[i]);
                        		death_mon = String.valueOf(death_num+1);
                        		if(Monster[death_num].getHp() == 0) {
                        			lblattack[death_num].setVisible(false);
                            		lblMonster[death_num].setVisible(false);
                            		s_mon = Arrays.stream(s_mon)
                            				.filter(item -> !item.equals(death_mon))
                            				.toArray(String[]::new);
                            		
                            		i--;
                            		try {
										Socket.exp_up(id, Monster[death_num].getExp());
									} catch (ClassNotFoundException | IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
                            		addBattleLog(ExpLog(monster_name(difficulty), Monster[death_num].getExp()));
                        			}
                    		}
                    		if(isStage_clear()) {
                            	//setVisible(false);
                    			try {
									Socket.setClear(id, location);
								} catch (ClassNotFoundException | IOException e1) {
									e1.printStackTrace();
								}
                    			if(difficulty ==0) {
                    				try {
										new RewardAPopup(GameForm.this, owner.getClass_Id(), floor);
									} catch (ClassNotFoundException | IOException e1) {
										e1.printStackTrace();
									}
                    			}
                    			else if(difficulty == 10) {
                    				new OverPopup(owner, exp, 1/*보스 클리어 시*/);
                    			}
                    			else {
                    				try {
										new RewardPopup(GameForm.this, owner.getClass_Id(), floor);
									} catch (ClassNotFoundException | IOException e1) {
										e1.printStackTrace();
									}
                    			}
                    			
                    			battle.setVisible(false);
                    			move.setVisible(true);
                    			}
                		}
                		now_mp -= Skill[btn].getUmana();
                    	Mpbar.setValue(now_mp);
                		Mpbar.setString(now_mp+"/"+max_mp);
                		btnSkill[btn].setVisible(false);
                		btnSkillex[btn].setVisible(false);
                	}
                	else {
                		JOptionPane.showMessageDialog(
                                GameForm.this,
                                "마나가 부족합니다.","마나 부족",JOptionPane.PLAIN_MESSAGE
                                );  
                	}
                	
                }
                	
    	});
        btnSkillex[btn].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(
                            GameForm.this,
                            " "+ Skill[btn].getName() + "\n" +
                            "마나 비용 :" + Skill[btn].getUmana()+ "\n" +
                            "스킬 설명 :" + Skill[btn].getExplan() + "\n",
                            "스킬 정보",JOptionPane.PLAIN_MESSAGE
                          );             
               }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	int choice = JOptionPane.showConfirmDialog(
                		GameForm.this,
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
    }
	private int monster_death(String n) {
    	return Integer.valueOf(n)-1;
    }
    private void addBattleLog(String log) {
    	BattleLog.append(log + "\n");  // 전투 기록에 내용 추가
    	BattleLog.setCaretPosition(BattleLog.getDocument().getLength());  // 맨 아래로 스크롤
    }
    private String monster_name(int difficulty) {
    	switch(difficulty) {
    		case 0: return "미믹";
    		case 1: return "슬라임";
    		case 2: return "뱀";
    		case 3: return "고블린";
    		case 4: return "유령";
    		case 5: return "킹슬라임";
    		case 6: return "식충식물";
    		case 7: return "스켈레톤";
    		case 8: return "골렘";
    		case 9: return "아이스골렘";
    		case 10: return "드래곤";
    		default: return "";
    	}
    }
    private void dungeonsetting(char map, int location) {
    	switch (map) {
		case 'a':
			if(location%5== 0) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/arrive_L.png"));
			}
			else if(location%5== 4) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/arrive_R.png"));
			}
			else {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/arrive_C.png"));
			}
			break;
		case 'd':
			if(location%5== 0) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/down_L.png"));
			}
			else if(location%5== 4) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/down_R.png"));
			}
			else {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/down_C.png"));
			}
			break;
		case 'r':
			if(location%5== 0) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/road_L.png"));
			}
			else if(location%5== 4) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/road_R.png"));
			}
			else {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/road_C.png"));
			}
			break;
		case 's':
			if(location%5== 0) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/start_L.png"));
			}
			else if(location%5== 4) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/store_R.png"));
			}
			break;
		case 'e':
			if(location%5== 1) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/beforeboss_L.png"));
			}
			else if(location%5== 3) {
				mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/beforeboss_R.png"));
			}
			break;
		case 'b':
			mapbg = new ImageIcon(GameForm.class.getResource("/image/dungeon/boss.png"));
			break;	
		default:
			break;
		}
    }
    private String StrLog(String attack, String defense, int damage) {
    	return attack+"(이)가 "+defense+"에게"+ damage +"만큼 피해를 입었습니다.";
    }
    private String ExpLog(String monster, int exp) {
    	return monster+"(을)를 처지했습니다."+ exp +"경험치 획득!";
    }
    private boolean isStage_clear() {
    	if(s_mon.length == 0) {
    		return true;
    	}
    	return false;
    }
    private void showFrame() {
        setTitle("픽셀 어드벤쳐");//타이틀
        setSize(800,600);//프레임의 크기
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
    public String getId() {
    	return id;
    }
    public void setGold(int gold) {
    	this.gold = gold;
    }
}
