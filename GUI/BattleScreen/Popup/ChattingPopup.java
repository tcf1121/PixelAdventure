package GUI.BattleScreen.Popup;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import GUI.MainScreen.WaitForm;
import Back.Server.MySocketClient;

public class ChattingPopup extends Thread{
    /**
	 * 
	 */
	JScrollPane scrollPane;
    ImageIcon icon;
    private JFrame Jf;
	private JButton btnSend;
	private JTextField tfSend;
	private JTextArea taChatting;
	private JLabel lblbg;
	private WaitForm owner;
	private String id;
	private String eid;
	public String roomnum;
	MySocketClient Socket;
	
	public ChattingPopup(WaitForm owner, String roomnum) throws ClassNotFoundException, IOException {
		this.roomnum = roomnum;
        this.owner = owner;
        id = owner.getId();
        try {
        	Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        eid = Socket.getEId(roomnum, id);




		icon = new ImageIcon(ChattingPopup.class.getResource("/image/popupbg/chattingbg.png"));
		Jf = new JFrame();
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

		bg.add(btnSend);
		bg.add(tfSend);
		bg.add(taChatting);

		bg.add(lblbg);
        scrollPane = new JScrollPane(bg);

        Jf.setContentPane(scrollPane);
        
        addListeners();
        
		showFrame();
	}
	//위치 설정
	private void init() {

		
		lblbg = new JLabel(new ImageIcon(ChattingPopup.class.getResource("/image/popupbg/chattingbg.png")));
		lblbg.setBounds(0, 0, 377, 491);

		btnSend = new JButton();
		btnSend.setBounds(322, 449, 33, 28);
		btnSend.setBorderPainted(false);
		btnSend.setContentAreaFilled(false);
		
		tfSend = new JTextField();
		tfSend.setBounds(20, 449, 307, 28);
		tfSend.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		tfSend.setBorder(null);
		
		taChatting = new JTextArea();
		taChatting.setEditable(false);
		taChatting.setBounds(20, 63, 335, 376);
		taChatting.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
	}
	
	
	//占쏙옙튼 占싱븝옙트 占쌓쇽옙
	private void addListeners() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		// 키보드 엔터키를 누르고, 입력값이 1이상일때만 전송되도록 합니다.
        		String Message = tfSend.getText().trim();
        		if (Message.length() > 0) {
        			try {
						Socket.sendMessage(id, Message);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			tfSend.setText(null);
        			AppendMessage(id, Message);
        		}
            }
            
        });
        tfSend.addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void keyPressed(KeyEvent e) { 
				// 키보드 엔터키를 누르고, 입력값이 1이상일때만 전송되도록 합니다.
				String Message = tfSend.getText().trim();
				if (e.getKeyCode() == KeyEvent.VK_ENTER && Message.length() > 0) {
					try {
						Socket.sendMessage(id, Message);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tfSend.setText(null);
					AppendMessage(id, Message);
				}
			}
		});
       
        Jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	int choice = JOptionPane.showConfirmDialog(
                		Jf,
                        "채팅창을 닫으시겠습니까?\n"
                        + "다시 열지 못합니다.",
                        "주의",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
            	if (choice == JOptionPane.YES_OPTION) {
            		Jf.dispose();
                }
                
            }
        });
        
    }


	public void AppendMessage(String id, String Message) {
		taChatting.append(id+": "+Message+"\n");
	}
    private void showFrame() {
        Jf.setTitle("채팅");//타이틀
        Jf.setSize(391,524);//프레임의 크기
        Point p = new Point();
        p.setLocation(owner.getLocation().getX()+
        		owner.getSize().getWidth(),
        		owner.getLocation().getY());
        Jf.setLocation(p);
        Jf.setDefaultCloseOperation(2);
        Jf.setResizable(false);//창의 크기를 변경하지 못하게
        Jf.setVisible(true);
    }
	public void run(){

		while (true) {
			try {
				Thread.sleep(200);
				String Message = Socket.getMessage(eid);
				if(Message != null) {
					AppendMessage(eid, Message);
				}
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
