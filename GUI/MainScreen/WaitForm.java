package GUI.MainScreen;

import javax.swing.*;

import Back.Datarelated.Ability;
import Back.Datarelated.User;
import Back.Server.MySocketClient;
import GUI.MainScreen.Popup.AbilityAPopup;
import GUI.MainScreen.Popup.SelectPopup;
import GUI.BattleScreen.RoomForm;
import GUI.GameScreen.GameForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class WaitForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private MainForm owner;
    private String Class_Id = "01";
    private JLabel lblLv;
    private JLabel lblNickName;
    public JLabel lblCharater1;
    public JLabel lblCharater2;
    public JLabel lblCharater3;
    private JButton btnDungeon;
    private JButton btnSingle;
    private JButton btnMulti;
    private JButton btnAbility;
    private JButton btnScharacter;
    private JButton btnSaveEnd;
    private String NickName = null;
    private User user;
    private String id;
    private int Lv;
    private ImageIcon Charicon1;
    private ImageIcon Charicon2;
    private ImageIcon Charicon3;
	private MySocketClient Socket;
    
    public WaitForm(MainForm owner) throws ClassNotFoundException, IOException {
        this.owner = owner;
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        id = owner.getTfId();
        user = Socket.getUser(id);
        Socket.deleteNAbility(id);
        Socket.setNAbility(Class_Id, id);        
        icon = new ImageIcon(WaitForm.class.getResource("/image/background/waitbg.png"));
        img_class(getClass_Id());
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
        

        bg.add(lblLv);
        bg.add(lblNickName);
        bg.add(lblCharater1);
        bg.add(lblCharater2);
        bg.add(lblCharater3);
        
        bg.add(btnDungeon);
        bg.add(btnMulti);
        bg.add(btnSingle);
        bg.add(btnAbility);
        bg.add(btnScharacter);
        bg.add(btnSaveEnd);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        addListeners();
        showFrame();

    }
    private void init() {
    	Lv = user.getLv();
    	NickName = user.getNickName();
		ImageIcon dungenicon = new ImageIcon(GameForm.class.getResource("/image/ui/dungeon.png"));
		ImageIcon singleicon = new ImageIcon(GameForm.class.getResource("/image/ui/singleplay.png"));
		ImageIcon multiicon = new ImageIcon(GameForm.class.getResource("/image/ui/multipaly.png"));
		ImageIcon abilityicon = new ImageIcon(GameForm.class.getResource("/image/ui/ability.png"));
		ImageIcon selectCharicon = new ImageIcon(GameForm.class.getResource("/image/ui/selectChar.png"));
		ImageIcon endgameicon = new ImageIcon(GameForm.class.getResource("/image/ui/endgame.png"));
		Color BColor = new Color(0, 0, 0);
        
        lblLv = new JLabel(String.valueOf(Lv));
        lblLv.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
        lblLv.setForeground(BColor);
        lblLv.setBounds(395, 59, 200, 20);
        lblNickName = new JLabel(NickName);
        lblNickName.setBounds(395, 80, 200, 40);
        lblNickName.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
        lblNickName.setForeground(BColor);
        lblCharater1 = new JLabel(Charicon1);
		lblCharater1.setBounds(313, 260, 200, 200);
		lblCharater2 = new JLabel(Charicon2);
		lblCharater2.setBounds(313, 260, 200, 200);
		lblCharater3 = new JLabel(Charicon3);
		lblCharater3.setBounds(313, 260, 200, 200);
		classlook(Class_Id);
        
        btnDungeon = new JButton(dungenicon);
        btnDungeon.setBounds(415, 225, 150, 75);
        btnDungeon.setBorderPainted(false);
        btnDungeon.setContentAreaFilled(false);
        
        btnSingle = new JButton(singleicon);
        btnSingle.setBounds(415, 225, 75, 75);
        btnSingle.setBorderPainted(false);
        btnSingle.setContentAreaFilled(false);
        btnSingle.setVisible(false);
        
        btnMulti = new JButton(multiicon);
        btnMulti.setBounds(490, 225, 75, 75);
        btnMulti.setBorderPainted(false);
        btnMulti.setContentAreaFilled(false);
        btnMulti.setVisible(false);
        
        btnAbility = new JButton(abilityicon);
        btnAbility.setBounds(65, 315, 150, 75);
        btnAbility.setBorderPainted(false);
        btnAbility.setContentAreaFilled(false);
        btnScharacter = new JButton(selectCharicon);
        btnScharacter.setBounds(315, 440, 150, 75);
        btnScharacter.setBorderPainted(false);
        btnScharacter.setContentAreaFilled(false);
        btnSaveEnd = new JButton(endgameicon);
        btnSaveEnd.setBounds(620, 480, 150, 75);
        btnSaveEnd.setBorderPainted(false);
        btnSaveEnd.setContentAreaFilled(false);
        
        
    }
    
	//버튼 이벤트 항수
    private void addListeners() {
    	btnDungeon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnDungeon.setVisible(false);
            	btnSingle.setVisible(true);
            	btnMulti.setVisible(true);
            }
        });
    	
        btnSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	Ability ab = null;
            	int exp = 0;
            	int startpoint = 0;
            	try {
					Socket.setNAbility(Class_Id, id);
					ab = Socket.getNAbility(id);
					exp = Socket.get_exp(id);
					Socket.DeleteMap(id);
					Socket.setMap(id, 1);//1층의 맵을 구성
					Socket.deleteUskill(id);
					Socket.Equipped_Start(id);
					Socket.SGskill(Class_Id, id);
					Socket.settingStore(Class_Id, id);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	try {
					new GameForm(WaitForm.this, 1/*층수*/, startpoint,
							ab.getHp(),0/*골드*/, 3/*포션*/, exp);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        btnMulti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
					new RoomForm(WaitForm.this);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
           }
        });
        
        
        
        btnScharacter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new SelectPopup(WaitForm.this);
            	
            }
        });
        btnAbility.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					new AbilityAPopup(WaitForm.this, Lv);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        btnSaveEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int choice = JOptionPane.showConfirmDialog(
                		WaitForm.this,
                        "로그아웃하시겠습니까?",
                        "로그아웃",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
            		try {
						Socket.logOut(user);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            		Socket.endServer();
                	System.exit(0);
                }
                
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	int choice = JOptionPane.showConfirmDialog(
                		WaitForm.this,
                        "게임을 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
            	if (choice == JOptionPane.YES_OPTION) {
            		try {
						Socket.logOut(user);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		Socket.endServer();
            		
                }
                
            }
        });
    }
    public String getId() {
		return id;
	}
    public String getClass_Id() {
    	return Class_Id;
    }
    public void setClass_Id(String Class_Id) {
    	this.Class_Id = Class_Id;
    }
    public String getNickname() {
    	return NickName;
    }
	private void img_class(String class_no) {
		Charicon1 = new ImageIcon(GameForm.class.getResource("/image/character/warrior_stand.gif"));
		Charicon2 = new ImageIcon(GameForm.class.getResource("/image/character/wizard_stand.gif"));
		Charicon3 = new ImageIcon(GameForm.class.getResource("/image/character/rouge_stand.gif"));
	}
	public void classlook(String class_no) {
		if(class_no == "01") {
			lblCharater1.setVisible(true);
			lblCharater2.setVisible(false);
			lblCharater3.setVisible(false);
		}
		else if(class_no == "02") {
			lblCharater1.setVisible(false);
			lblCharater2.setVisible(true);
			lblCharater3.setVisible(false);
		}
		else if(class_no == "03") {
			lblCharater1.setVisible(false);
			lblCharater2.setVisible(false);
			lblCharater3.setVisible(true);
		}
	}
	
    private void showFrame() {
    	setTitle("픽셀 어드벤쳐");
        setSize(800,600);
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
    
    

}