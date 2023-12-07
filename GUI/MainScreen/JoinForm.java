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


public class JoinForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private MainForm owner;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JPasswordField tfRe;
    private JTextField tfNickName;
    private JButton btnJoin;
    private JButton btnCancel;
    private MySocketClient Socket;
    
    public JoinForm(MainForm owner) {
        this.owner = owner;
        
        icon = new ImageIcon(JoinForm.class.getResource("/image/background/joinbg.png"));
        
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
        bg.add(tfId);
        bg.add(tfPw);
        bg.add(tfRe);
        bg.add(tfNickName);
        bg.add(btnJoin);
        bg.add(btnCancel);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        addListeners();
        showFrame();
    }
    private void init() {
        // 크기 고정
    	ImageIcon joinicon = new ImageIcon(JoinForm.class.getResource("/image/ui/join.png"));
    	ImageIcon cancleicon = new ImageIcon(JoinForm.class.getResource("/image/ui/cancle.png"));
    	Font font = new Font("나눔고딕 ExtraBold", Font.BOLD, 20);

        tfId = new JTextField();
        tfId.setFont(font);
        tfPw = new JPasswordField();
        tfPw.setFont(font);
        tfRe = new JPasswordField();
        tfRe.setFont(font);
        tfNickName = new JTextField();
        tfNickName.setFont(font);
        
        btnJoin = new JButton(joinicon);
        btnJoin.setBorderPainted(false);
        btnJoin.setContentAreaFilled(false);
        btnCancel = new JButton(cancleicon);
        btnCancel.setBorderPainted(false);
        btnCancel.setContentAreaFilled(false);
        
        tfId.setBounds(380, 237, 120, 30);
        tfPw.setBounds(380, 271, 120, 30);
        tfRe.setBounds(380, 305, 120, 30);
        tfNickName.setBounds(380, 339, 120, 30);
        btnJoin.setBounds(280, 400, 100, 50);
        btnCancel.setBounds(405, 400, 100, 50);

    }
    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // 정보 하나라도 비어있으면
                if(isBlank()) {
                    JOptionPane.showMessageDialog(
                            JoinForm.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Id 중복일 때
                    try {
						if(Socket.isIdOverlap(tfId.getText())) {
						    JOptionPane.showMessageDialog(
						            JoinForm.this,
						            "이미 존재하는 Id입니다."
						    );
						    tfId.requestFocus();

						    // Pw와 Re가 일치하지 않았을 때
						} else if(!String.valueOf(tfPw.getPassword()).equals(String.valueOf(tfRe.getPassword()))) {
						    JOptionPane.showMessageDialog(
						            JoinForm.this,
						            "PW와 PW확인이 일치하지 않습니다."
						    );
						    tfPw.requestFocus();
						} else {
							User newUser = new User(
									tfId.getText(),
						            String.valueOf(tfPw.getPassword()),
						            tfNickName.getText());
							Socket.Join(newUser);
						    JOptionPane.showMessageDialog(
						            JoinForm.this,
						            "회원가입을 완료했습니다!"
						    );
						    dispose();
						    owner.setVisible(true);
						}
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                		JoinForm.this,
                        "게임을 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    public boolean isBlank() {
        boolean result = false;
        if(tfId.getText().isEmpty()) {
            tfId.requestFocus();
            return true;
        }
        if(String.valueOf(tfPw.getPassword()).isEmpty()) {
            tfPw.requestFocus();
            return true;
        }
        if(String.valueOf(tfRe.getPassword()).isEmpty()) {
            tfRe.requestFocus();
            return true;
        }
        if(tfNickName.getText().isEmpty()) {
            tfNickName.requestFocus();
            return true;
        }
        return result;
    }
    

    private void showFrame() {
        setTitle("픽셀 어드벤쳐");//타이틀
        setSize(800,600);//프레임의 크기
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
}