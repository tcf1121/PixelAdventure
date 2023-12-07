package GUI.BattleScreen;

import javax.swing.*;

import Back.Datarelated.User;
import Back.Server.MySocketClient;
import GUI.BattleScreen.Popup.CreatePopup;
import GUI.BattleScreen.Popup.FindPopup;
import GUI.MainScreen.WaitForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class RoomForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private WaitForm owner;
    private String Class_Id;
    public JLabel lblCharater1;
    public JLabel lblCharater2;
    public JLabel lblCharater3;
    private JButton btnCreate;
    private JButton btnFind;
    private JButton btnBack;
    private User user;
    private String id;
    private String NickName;
	private MySocketClient Socket;
    
    public RoomForm(WaitForm owner) throws ClassNotFoundException, IOException {
        this.owner = owner;
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        id = owner.getId();
        NickName = owner.getNickname();
        Class_Id = owner.getClass_Id();
        user = Socket.getUser(id);
        Socket.deleteNAbility(id);
        Socket.deleteUskill(id);
        Socket.setBAbility(Class_Id, id);
        Socket.deleteUskill(id);
        Socket.BattleSkill(id, Class_Id);
        icon = new ImageIcon(RoomForm.class.getResource("/image/background/roombg.png"));
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
        bg.add(btnCreate);
        bg.add(btnFind);
        bg.add(btnBack);


        scrollPane = new JScrollPane(bg);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(RoomForm.class.getResource("/image/background/roombg.png")));
        lblNewLabel.setBounds(0, 0, 786, 563);
        bg.add(lblNewLabel);
        setContentPane(scrollPane);
        addListeners();
        showFrame();

    }
    private void init() {      
        btnCreate = new JButton();
        btnCreate.setBounds(276, 135, 221, 60);
        btnCreate.setBorderPainted(false);
        btnCreate.setContentAreaFilled(false);
        
        btnFind = new JButton();
        btnFind.setBounds(276, 254, 221, 60);
        btnFind.setBorderPainted(false);
        btnFind.setContentAreaFilled(false);
        
        btnBack = new JButton();
        btnBack.setBounds(276, 367, 221, 60);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
    }
    
	//버튼 이벤트 항수
    private void addListeners() {
    	btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					CreatePopup cp = new CreatePopup(RoomForm.this);
					cp.start();
				} catch (ClassNotFoundException | IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    	btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					new FindPopup(RoomForm.this);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    	btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	owner.setVisible(true);
            }
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	int choice = JOptionPane.showConfirmDialog(
                		RoomForm.this,
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
            		System.exit(0);
                }
                
            }
        });
    }
    
	
    private void showFrame() {
    	setTitle("픽셀 어드벤쳐");
        setSize(800,600);
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
    
    public String getId() {
		return id;
	}
    public String getNickname() {
    	return NickName;
    }
    public WaitForm getwait() {
    	return owner;
    }
}