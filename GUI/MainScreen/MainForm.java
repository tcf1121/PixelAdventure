package GUI.MainScreen;

import javax.swing.*;

import Back.Datarelated.User;
import Back.Server.MySocketClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
 
public class MainForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnJoin;
    private JLabel lblui;
    private MySocketClient Socket;
    
    public MainForm() {

        icon = new ImageIcon(MainForm.class.getResource("/image/background/loginbg.gif"));
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        bg.add(lblui);
        lblui.add(tfId);
        lblui.add(tfPw);
        lblui.add(btnLogin);
        lblui.add(btnJoin);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        
        addListeners();
        showFrame();
    }
    public void init() {
    	ImageIcon loginicon = new ImageIcon(MainForm.class.getResource("/image/ui/login.png"));
    	ImageIcon joinicon = new ImageIcon(MainForm.class.getResource("/image/ui/join.png"));
    	ImageIcon uiicon = new ImageIcon(MainForm.class.getResource("/image/background/loginbg.gif"));
    	Font font = new Font("나눔고딕 ExtraBold", Font.BOLD, 20);
        
        lblui = new JLabel(uiicon);
        tfId = new JTextField();
        tfId.setFont(font);
        tfPw = new JPasswordField();
        tfPw.setFont(font);
        
        btnLogin = new JButton(loginicon);
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
        btnJoin = new JButton(joinicon);
        btnJoin.setBorderPainted(false);
        btnJoin.setContentAreaFilled(false);
        
        lblui.setBounds(0, 0, 781, 558);
        tfId.setBounds(330, 418, 170, 30);
        tfPw.setBounds(330, 452, 170, 30);
        btnLogin.setBounds(280, 485, 100, 50);
        btnJoin.setBounds(405, 485, 100, 50);
        
    }
    public void showFrame() {
        setTitle("픽셀 어드벤쳐");
        setSize(800,600);
        setLocationRelativeTo(null);//화면을 가운데
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);//창 크기 고정
    }

    public void addListeners() {

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new JoinForm(MainForm.this);
                tfId.setText("");
                tfPw.setText("");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디 입력 x
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(MainForm.this,
                            "아이디를 입력하시오.",
                            "아이디 입력",
                            JOptionPane.WARNING_MESSAGE);

                    // 회원가입된 아이디 입력 o
                } else
					try {

						if (Socket.isIdOverlap(tfId.getText())) {
							User loginuser = new User(tfId.getText(),
									String.valueOf(tfPw.getPassword()), "");
						    // 비밀번호 입력 x
						    if(String.valueOf(tfPw.getPassword()).isEmpty()) {
						        JOptionPane.showMessageDialog(
						        		MainForm.this,
						                "비밀번호를 입력하시오.",
						                "비밀번호 입력",
						                JOptionPane.WARNING_MESSAGE);
						     
						    }else if(Socket.ChecklogIn(tfId.getText())) {//이미 로그인된 아이디
						    	JOptionPane.showMessageDialog(
						        		MainForm.this,
						                "이미 로그인 된 아이디 입니다.");
							}else if (Socket.logIn(loginuser)) {// 모두 잘 입력됨
						    	setVisible(false);
						        new WaitForm(MainForm.this);
						        tfId.setText("");
						        tfPw.setText("");
						     // 비밀번호 틀림 
						    } else {
						    	JOptionPane.showMessageDialog(
						        		MainForm.this,
						                "비밀번호를 잘못 입력하셨습니다.");
						    }
						    // 회원 가입 되지 않은 아이디 입력
						} else {
						    JOptionPane.showMessageDialog(
						    		MainForm.this,
						            "없는 아이디 입니다."
						         
						    );
						
						}
					} catch (HeadlessException | ClassNotFoundException |IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		MainForm.this,
                        "종료하시겠습니까?",
                        "게임 종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
    public String getTfId() {
		return tfId.getText();
	}
    
    public static void main(String[] args) {
    	MainForm frame = new MainForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
