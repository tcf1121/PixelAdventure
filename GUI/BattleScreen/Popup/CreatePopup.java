package GUI.BattleScreen.Popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Back.Server.MySocketClient;
import GUI.BattleScreen.BattleForm;
import GUI.BattleScreen.RoomForm;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class CreatePopup extends Thread{
	JScrollPane scrollPane;
    ImageIcon icon;
	private JButton btncancle;
	private JLabel lblbg;
	private JLabel lblroomnum;
	private String  roomnum;
	private RoomForm owner;
	private MySocketClient Socket;
	private JFrame popup;
	
	public CreatePopup(RoomForm owner) throws ClassNotFoundException, IOException, InterruptedException {
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    icon = new ImageIcon(CreatePopup.class.getResource("/image/popupbg/createbg.png"));
	    popup = new JFrame(); 
		this.owner = owner;
        //배경 Panel 생성후 컨텐츠페인으로 지정
		this.roomnum = Socket.getRoomnum();
		Socket.makeRoom(roomnum, owner.getId());
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
	    scrollPane = new JScrollPane(bg);

	    bg.add(lblroomnum);
	    bg.add(btncancle);
	    bg.add(lblbg);
	    showFrame();
	    popup.setContentPane(scrollPane);
	    
	    addListeners();
	    
	}
	public void run(){
	    try {
			do {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (!Socket.CheckFull(roomnum));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(
        		popup,
                "접속을 시작합니다."
        );
		popup.dispose();
		owner.dispose();
		try {
			BattleForm BF = new BattleForm(owner.getwait(), roomnum, 1);
			BF.start();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void init() {		
		lblbg = new JLabel();
	    lblbg.setIcon(icon);
	    lblbg.setBounds(0, 0, 296, 179);
		lblroomnum = new JLabel(roomnum);
		lblroomnum.setBounds(96, 86, 107, 38);
        lblroomnum.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 25));
        lblroomnum.setForeground(new Color(255, 255, 255));
        lblroomnum.setHorizontalAlignment(JLabel.CENTER);
	    btncancle = new JButton();
	    btncancle.setBorderPainted(false);
	    btncancle.setContentAreaFilled(false);
	    btncancle.setBounds(105, 129, 87, 38);
	}
	
    private void showFrame() {
        popup.setSize(296, 179);//프레임의 크기
        popup.setUndecorated(true);
        Point p = new Point();
        p.setLocation(owner.getLocation().getX()+
        		(owner.getSize().getWidth()-popup.getSize().getWidth())/2,
        		owner.getLocation().getY()+
        		(owner.getSize().getHeight()-popup.getSize().getHeight())/2);
        popup.setLocation(p);
        popup.setDefaultCloseOperation(2);
        popup.setResizable(false);//창의 크기를 변경하지 못하게
        popup.setVisible(true);
    }
	
    private void addListeners() {

        btncancle.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		try {
					Socket.deleteRoom(roomnum);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		popup.dispose();
        	}
    	});
    }
}
