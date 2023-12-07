package GUI.Manager;

import javax.swing.*;

import Back.Datarelated.UserDataSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
public class SeverLoginForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDataSet users;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    
    public SeverLoginForm() {
    	users = new UserDataSet();
        icon = new ImageIcon(SeverLoginForm.class.getResource("/image/background/Mloginbg.png"));
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
        bg.add(btnLogin);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        
        addListeners();
        showFrame();
    }
    public void init() {
    	ImageIcon loginicon = new ImageIcon(SeverLoginForm.class.getResource("/image/ui/Mlogin.png"));
    	Font font = new Font("나눔고딕 ExtraBold", Font.BOLD, 20);
        
        tfId = new JTextField();
        tfId.setFont(font);
        tfPw = new JPasswordField();
        tfPw.setFont(font);
        
        btnLogin = new JButton(loginicon);
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
        
        //lblui.setVisible(false);
        tfId.setBounds(330, 418, 170, 30);
        tfPw.setBounds(330, 452, 170, 30);
        btnLogin.setBounds(340, 485, 100, 50);
        
        
    }
    public void showFrame() {
        setTitle("픽셀 어드벤쳐 메인 서버 로그인");
        setSize(800,600);
        setLocationRelativeTo(null);//화면을 가운데
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫았을 때 메모리에서 제거되도록 설정합니다.
        setResizable(false);//창 크기 고정
        setVisible(true);
    }

    public void addListeners() {
        
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디 입력 x
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(SeverLoginForm.this,
                            "아이디를 입력하시오.",
                            "아이디 입력",
                            JOptionPane.WARNING_MESSAGE);

                    // 관리자 아이디 입력 o
                } else if (users.isIdManager(tfId.getText())) {

                    // 비밀번호 입력 x
                    if(String.valueOf(tfPw.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(
                        		SeverLoginForm.this,
                                "비밀번호를 입력하시오.",
                                "비밀번호 입력",
                                JOptionPane.WARNING_MESSAGE);

                        // 비밀번호 틀림
                    } else if (!users.getUser(tfId.getText()).getPw().equals(String.valueOf(tfPw.getPassword()))) {
                        JOptionPane.showMessageDialog(
                        		SeverLoginForm.this,
                                "비밀번호를 잘못 입력하셨습니다.");

                        // 모두 잘 입력됨
                    } else {
                    	setVisible(false);
                        tfId.setText("");
                        tfPw.setText("");
                        new ManagerForm(SeverLoginForm.this);
                    }
                    // 관리자가 아닌 아이디 입력
                } else {
                    JOptionPane.showMessageDialog(
                    		SeverLoginForm.this,
                            "관리자가 아닙니다."
                         
                    );
                
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		SeverLoginForm.this,
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
   
    public UserDataSet getUsers() {
    	return users;
    }
    
    public String getTfId() {
		return tfId.getText();
	}
    
    public static void main(String[] args) {
    	SeverLoginForm frame = new SeverLoginForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
