package GUI.GameScreen.Popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import Back.Datarelated.Equip;
import Back.Datarelated.Skill;
import Back.Datarelated.Store;
import Back.Server.MySocketClient;
import GUI.GameScreen.GameForm;


public class StorePopup extends JDialog{
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    private GameForm owner;
	
	private int state = 0;
    ImageIcon icon;
    private ImageIcon buttonO = new ImageIcon(StorePopup.class.getResource("/image/ui/StorebuttonO.png"));
    private ImageIcon buttonX = new ImageIcon(StorePopup.class.getResource("/image/ui/StorebuttonX.png"));
    private ImageIcon special1icon = new ImageIcon(StorePopup.class.getResource("/image/special/01.png"));
    private ImageIcon special2icon = new ImageIcon(StorePopup.class.getResource("/image/special/02.png"));
    private ImageIcon special3icon = new ImageIcon(StorePopup.class.getResource("/image/special/03.png"));
    private ImageIcon skill1icon;
    private ImageIcon skill2icon;
    private ImageIcon skill3icon;
    private ImageIcon equip1icon;
    private ImageIcon equip2icon;
    private ImageIcon equip3icon;
    
     
	private Skill[] Skill = {null, null, null};
	private int[] Skillnum = {0 , 0, 0};
	
	private Equip[] Equip = {null, null, null};
	private int[] Equipnum = {0 , 0, 0};
	
	private Store store;


	
	private JButton btnstate0;
	private JButton btnstate1;
	private JButton btnstate2;
	private JLabel lblstate0;
	private JLabel lblstate1;
	private JLabel lblstate2;
	
	private JButton btnGoods1;
	private JButton btnGoods2;
	private JButton btnGoods3;
	
	private JLabel lblHoldingG;
	private JLabel lblGcount1;
	private JLabel lblGcount2;
	private JLabel lblGcount3;
	
	private JButton btnBuy1;
	private JButton btnBuy2;
	private JButton btnBuy3;
	
	private JButton btnClose;
	
	private int price0[] = {15, 15, 7};
	private int price1[] = {0, 0, 0};
	private int price2[] = {0, 0, 0};
	
	private String id;
	private int gold;
	private MySocketClient Socket;
	
	public StorePopup(GameForm owner, String class_no, int gold) throws ClassNotFoundException, IOException {
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		this.owner = owner;
        icon = new ImageIcon(StorePopup.class.getResource("/image/popupbg/Storepopup.png"));
        this.id = owner.getId();
        this.gold = gold;
        this.store = Socket.getStore(id);
        Equipnum = Socket.getEquip(id); 
        Skillnum = Socket.getSkill(id);
        
        for(int i = 0;i < 3; i++) {
        	Equip[i] = Socket.infoEquip(Integer.toString(Equipnum[i]));
        	System.out.println("장비"+Equip[i].getId());
        }
        for(int i = 0;i < 3; i++) {
        	
        	Skill[i] = Socket.infoSkill(Integer.toString(Skillnum[i]));
        	System.out.println("스킬"+Skill[i].getId());
        }
        for(int i = 0;i < 3; i++) {
        	price1[i] = (Skillnum[i]%10)*2;
        }
        for(int i = 0;i < 3; i++) {
        	price2[i] = Equipnum[i] > 20? 20:15;
        }
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
        bg.add(btnstate0); 	bg.add(btnstate1);
        bg.add(btnstate2);	bg.add(lblstate0);
        bg.add(lblstate1);	bg.add(lblstate2);
        bg.add(lblHoldingG);bg.add(lblGcount1);
        bg.add(lblGcount2);	bg.add(lblGcount3);
        bg.add(btnGoods1);	bg.add(btnGoods2);
        bg.add(btnGoods3);	bg.add(btnBuy1);
        bg.add(btnBuy2);	bg.add(btnBuy3);
        bg.add(btnClose);
        
        
        
        scrollPane = new JScrollPane(bg);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(StorePopup.class.getResource("/image/popupbg/Storepopup.png")));
        lblNewLabel.setBounds(0, 0, 657, 391);
        bg.add(lblNewLabel);
        

        


        setContentPane(scrollPane);
        addListeners();
        
        showFrame();
    }
	private void init() {
		Font font = new Font("SansSerif", 1, 20);
        Color WColor = new Color(255, 255, 255);
        //System.out.println(Skill[0].getId()+","+Skill[1].getId()+","+Skill[2].getId());
        skill1icon = new ImageIcon(StorePopup.class.getResource("/image/skill/"+Skill[0].getId()+".png"));
        skill2icon = new ImageIcon(StorePopup.class.getResource("/image/skill/"+Skill[1].getId()+".png"));
        skill3icon = new ImageIcon(StorePopup.class.getResource("/image/skill/"+Skill[2].getId()+".png"));
		equip1icon = new ImageIcon(StorePopup.class.getResource("/image/equipment/"+Equip[0].getId()+".png"));
		equip2icon = new ImageIcon(StorePopup.class.getResource("/image/equipment/"+Equip[1].getId()+".png"));
		equip3icon = new ImageIcon(StorePopup.class.getResource("/image/equipment/"+Equip[2].getId()+".png"));

		btnstate0 = new JButton(buttonO);
        btnstate0.setBorderPainted(false);
        btnstate0.setContentAreaFilled(false);
        btnstate0.setBounds(9, 30, 30, 69); 
		btnstate1 = new JButton(buttonX);
        btnstate1.setBorderPainted(false);
        btnstate1.setContentAreaFilled(false);
        btnstate1.setBounds(9, 100, 30, 69); 
		btnstate2 = new JButton(buttonX);
        btnstate2.setBorderPainted(false);
        btnstate2.setContentAreaFilled(false);
        btnstate2.setBounds(9, 170, 30, 69); 
			
        lblstate0 = new JLabel(new ImageIcon(StorePopup.class.getResource("/image/ui/splabel.png")));
        lblstate0.setBounds(9, 30, 30, 69); 
        lblstate1 = new JLabel(new ImageIcon(StorePopup.class.getResource("/image/ui/slabel.png")));
        lblstate1.setBounds(9, 100, 30, 69); 
        lblstate2 = new JLabel(new ImageIcon(StorePopup.class.getResource("/image/ui/elabel.png")));
        lblstate2.setBounds(9, 170, 30, 69); 
        
        
        btnGoods1 = new JButton(special1icon);
        btnGoods1.setBorderPainted(false);
        btnGoods1.setContentAreaFilled(false);
        btnGoods1.setBounds(97, 104, 80, 120);        
        btnGoods2 = new JButton(special2icon);
        btnGoods2.setContentAreaFilled(false);
        btnGoods2.setBorderPainted(false);
        btnGoods2.setBounds(290, 104, 80, 120);        
        btnGoods3 = new JButton(special3icon);
        btnGoods3.setContentAreaFilled(false);
        btnGoods3.setBorderPainted(false);
        btnGoods3.setBounds(482, 104, 80, 120);
				
        lblHoldingG = new JLabel(String.valueOf(gold)+"G");
		lblHoldingG.setHorizontalAlignment(JLabel.RIGHT);
		lblHoldingG.setFont(font);
		lblHoldingG.setForeground(WColor);
		lblHoldingG.setBounds(482, 41, 80, 20);
        
        
		lblGcount1 = new JLabel(String.valueOf(price0[0])+"G");
		lblGcount1.setHorizontalAlignment(JLabel.CENTER);
		lblGcount1.setFont(font);
		lblGcount1.setForeground(WColor);
		lblGcount1.setBounds(97, 238, 80, 20);
		
        lblGcount2 = new JLabel(String.valueOf(price0[1])+"G");
        lblGcount2.setHorizontalAlignment(JLabel.CENTER);
        lblGcount2.setForeground(Color.WHITE);
        lblGcount2.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblGcount2.setBounds(290, 238, 80, 20);
		
        lblGcount3 = new JLabel(String.valueOf(price0[2])+"G");
        lblGcount3.setHorizontalAlignment(JLabel.CENTER);
        lblGcount3.setForeground(Color.WHITE);
        lblGcount3.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblGcount3.setBounds(482, 238, 80, 20);
		

        
        btnBuy1 = new JButton();
        btnBuy1.setBorderPainted(false);
        btnBuy1.setContentAreaFilled(false);
        btnBuy1.setBounds(95, 268, 85, 28);
        
        btnBuy2 = new JButton();
        btnBuy2.setContentAreaFilled(false);
        btnBuy2.setBorderPainted(false);
        btnBuy2.setBounds(287, 268, 85, 28);
        
        btnBuy3 = new JButton();
        btnBuy3.setContentAreaFilled(false);
        btnBuy3.setBorderPainted(false);
        btnBuy3.setBounds(480, 268, 85, 28);
        
        btnClose = new JButton();
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setBounds(287, 322, 86, 35);
    }
    
    private void showFrame() {
        setSize(659, 393);//프레임의 크기
        setUndecorated(true);
        Point p = new Point();
        p.setLocation(owner.getLocation().getX()+
        		(owner.getSize().getWidth()-getSize().getWidth())/2,
        		owner.getLocation().getY()+
        		(owner.getSize().getHeight()-getSize().getHeight())/2);
        setLocation(p);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
	
    private void addListeners() {
    	btnstate0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	state = 0;
            	btnstate0.setIcon(buttonO);
            	btnstate1.setIcon(buttonX);
            	btnstate2.setIcon(buttonX);
            	btnGoods1.setIcon(special1icon);
            	btnGoods2.setIcon(special2icon);
            	btnGoods3.setIcon(special3icon);
            	lblGcount1.setText(String.valueOf(price0[0])+"G");
            	lblGcount2.setText(String.valueOf(price0[1])+"G");
            	lblGcount3.setText(String.valueOf(price0[2])+"G");
            }
    	});
    	btnstate1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	state = 1;
            	btnstate0.setIcon(buttonX);
            	btnstate1.setIcon(buttonO);
            	btnstate2.setIcon(buttonX);
            	btnGoods1.setIcon(skill1icon);
            	btnGoods2.setIcon(skill2icon);
            	btnGoods3.setIcon(skill3icon);
            	lblGcount1.setText(String.valueOf(price1[0])+"G");
            	lblGcount2.setText(String.valueOf(price1[1])+"G");
            	lblGcount3.setText(String.valueOf(price1[2])+"G");
            }
    	});
    	btnstate2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	state = 2;
            	btnstate0.setIcon(buttonX);
            	btnstate1.setIcon(buttonX);
            	btnstate2.setIcon(buttonO);
            	btnGoods1.setIcon(equip1icon);
            	btnGoods2.setIcon(equip2icon);
            	btnGoods3.setIcon(equip3icon);
            	lblGcount1.setText(String.valueOf(price2[0])+"G");
            	lblGcount2.setText(String.valueOf(price2[1])+"G");
            	lblGcount3.setText(String.valueOf(price2[2])+"G");
            }
    	});
    	
    	btnGoods1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            			switch(state) {
            			case 0:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                                    "포션 사용횟수가 1회 증가합니다.",
                                    "특수 능력 정보",JOptionPane.PLAIN_MESSAGE
                                    ); 
            				break;
            			case 1:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                        			" "+ Skill[0].getName() + "\n" +
                                    "마나 비용 :" + Skill[0].getUmana()+ "\n" +
                                    "설명 :" + Skill[0].getExplan() + "\n",
                                    "스킬 정보",JOptionPane.PLAIN_MESSAGE      
                                    ); 
            				break;
            			case 2:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                        			" "+ Equip[0].getName() + "\n" +
                                    "희귀도 :" + Equip[0].getRarity()+ "\n" +
                                    "설명 :" + Equip[0].getExplan()+ "\n" +
                                    "효과 :" + Equip[0].getEf_ex() + "\n",
                                    "장비설명",JOptionPane.PLAIN_MESSAGE     
                                    );
            				break;
        				default:
        					break;
            			}
                        
            }
    	});
    	btnGoods2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            			switch(state) {
            			case 0:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                                    "한턴에 사용할 수 있는 카드가 5개로 증가합니다.",
                                    "특수 능력 정보",JOptionPane.PLAIN_MESSAGE
                                    ); 
            				break;
            			case 1:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                        			" "+ Skill[1].getName() + "\n" +
                                    "마나 비용 :" + Skill[1].getUmana()+ "\n" +
                                    "설명 :" + Skill[1].getExplan() + "\n",
                                    "스킬 정보",JOptionPane.PLAIN_MESSAGE      
                                    ); 
            				break;
            			case 2:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                        			" "+ Equip[1].getName() + "\n" +
                                    "희귀도 :" + Equip[1].getRarity()+ "\n" +
                                    "설명 :" + Equip[1].getExplan()+ "\n" +
                                    "효과 :" + Equip[1].getEf_ex() + "\n",
                                    "장비설명",JOptionPane.PLAIN_MESSAGE     
                                    );
            				break;
        				default:
        					break;
            			}
                        
            }
    	});
    	btnGoods3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            			switch(state) {
            			case 0:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                                    "사용중인 카드에 마나 회복 카드를 추가합니다.",
                                    "특수 능력 정보",JOptionPane.PLAIN_MESSAGE
                                    ); 
            				break;
            			case 1:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                        			" "+ Skill[2].getName() + "\n" +
                                    "마나 비용 :" + Skill[2].getUmana()+ "\n" +
                                    "설명 :" + Skill[2].getExplan() + "\n",
                                    "스킬 정보",JOptionPane.PLAIN_MESSAGE      
                                    ); 
            				break;
            			case 2:
                        	JOptionPane.showMessageDialog(
                        			StorePopup.this,
                        			" "+ Equip[2].getName() + "\n" +
                                    "희귀도 :" + Equip[2].getRarity()+ "\n" +
                                    "설명 :" + Equip[2].getExplan()+ "\n" +
                                    "효과 :" + Equip[2].getEf_ex() + "\n",
                                    "장비설명",JOptionPane.PLAIN_MESSAGE     
                                    );
            				break;
        				default:
        					break;
            			}
                        
            }
    	});
    	btnBuy1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		StorePopup.this,
                        "구매하시겠습니까?",
                        "상품 구매",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                	switch (state) {
    				case 0:
    					if(gold >= price0[0] && store.getPotion() == 0) {
    						gold -= price0[0];
    						try {
								Socket.buyPotion(id);
								store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}			
    						owner.Nowpotion++;
    					}else if(store.getPotion() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					}				
    					break;
    				case 1:
    					if(gold >= price1[0] && store.getSkill1() == 0) {
    						gold -= price1[0];
    						try {
								Socket.buySkill1(id);
								Socket.addUskill(id, String.valueOf(Skillnum[0]));
	        					store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getSkill1() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					}   					
    					break;
    				case 2:
    					if(gold >= price2[0] && store.getEquipment1() == 0) {
    						gold -= price2[0];
        					try {
        						Socket.buyEquipment1(id);
								Socket.Equipped(id, String.valueOf(Equipnum[0]));
								store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getEquipment1() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					} 
    					
    					break;
    				default:
    					break;
    				}
                	lblHoldingG.setText(String.valueOf(gold)+"G");
                }
            	
            }
    	});
    	btnBuy2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int choice = JOptionPane.showConfirmDialog(
                		StorePopup.this,
                        "구매하시겠습니까?",
                        "상품 구매",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                	switch (state) {
    				case 0:
    					if(gold >= price0[1] && store.getSkill_card() == 0) {
    						gold -= price0[1];
    						try {
    							Socket.buySkill_card(id);
								store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getSkill_card() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					} 					
    					break;
    				case 1:
    					if(gold >= price1[1] && store.getSkill2() == 0) {
    						gold -= price1[1];
    						try {
								Socket.buySkill2(id);
								Socket.addUskill(id, String.valueOf(Skillnum[1]));
								store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getSkill2() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					} 	
    					
    					break;
    				case 2:
    					if(gold >= price2[1] && store.getEquipment2() == 0) {
    						gold -= price2[1];
    						try {
    							Socket.buyEquipment2(id);
        						Socket.Equipped(id, String.valueOf(Equipnum[1]));
								store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getEquipment2() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					} 						
    					break;
    				default:
    					break;
    				}
                	lblHoldingG.setText(String.valueOf(gold)+"G");
                }
            	
            	
            }
    	});
    	btnBuy3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int choice = JOptionPane.showConfirmDialog(
                		StorePopup.this,
                        "구매하시겠습니까?",
                        "상품 구매",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                	switch (state) {
    				case 0:
    					if(gold >= price0[2] && store.getManaskill() == 0) {
    						gold -= price0[2];
    						try {
								Socket.buyManaskill(id);
								store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getManaskill() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					} 					
    					break;
    				case 1:
    					if(gold >= price1[2] && store.getSkill3() == 0) {
    						gold -= price1[2];
    						try {
								Socket.buySkill3(id);
								Socket.addUskill(id, String.valueOf(Skillnum[2]));
	    						store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getSkill3() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					} 	
    					
    					break;
    				case 2:
    					if(gold >= price2[2] && store.getEquipment3() == 0) {
    						gold -= price2[2];
    						try {
								Socket.buyEquipment3(id);
								Socket.Equipped(id, String.valueOf(Equipnum[2]));
	    						store = Socket.getStore(id);
							} catch (ClassNotFoundException | IOException e1) {
								e1.printStackTrace();
							}
    					}else if(store.getEquipment3() != 0) {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "이미 구매한 상품입니다.",
    		                        "품절",JOptionPane.PLAIN_MESSAGE
    		                        );   
    					}else {
    						JOptionPane.showMessageDialog(
    		                        StorePopup.this,
    		                        "재화가 부족합니다.",
    		                        "재화 부족",JOptionPane.PLAIN_MESSAGE
    		                        );    
    					}
    				default:
    					break;
    				}
                	lblHoldingG.setText(String.valueOf(gold)+"G");
                }
            	
            }
    	});
    	btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int choice = JOptionPane.showConfirmDialog(
                		StorePopup.this,
                        "상점에 나가시겠습니까?",
                        "나가기",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                	owner.setGold(gold);
                	dispose();
                }
            }
    	});
    }
}
