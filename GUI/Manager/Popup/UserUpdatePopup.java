package GUI.Manager.Popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Back.Datarelated.UserDataSet;
import GUI.Manager.AccountForm;


public class UserUpdatePopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private JButton btnX;
	private JButton btnCancle;
	private JButton btnupdate;

	
	private AccountForm owner;
	private JTextField tfId;
	private JTextField tfPw;
	private JTextField textNickname;
	
	private UserDataSet users;
	
	public UserUpdatePopup(AccountForm owner) {
		this.owner = owner;
    	users = new UserDataSet();
	    icon = new ImageIcon(UserUpdatePopup.class.getResource("/image/popupbg/userupdatebg.png"));
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        JPanel bg = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                g.drawImage(null, 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
	    
	    bg.setLayout(null);
	    init();


	    scrollPane = new JScrollPane(bg);
	    
	    JLabel lblNewLabel = new JLabel("New label");
	    lblNewLabel.setIcon(icon);
	    lblNewLabel.setBounds(0, -5, 377, 294);
	    lblNewLabel.add(btnX);
	    lblNewLabel.add(btnCancle);
	    lblNewLabel.add(btnupdate);
	    lblNewLabel.add(tfId);
	    lblNewLabel.add(tfPw);
	    lblNewLabel.add(textNickname);
	    bg.add(lblNewLabel);


	    

	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {
		Font font = new Font("SansSerif", 1, 20);

        
        btnX = new JButton();
		btnX.setBorderPainted(false);
		btnX.setContentAreaFilled(false);
		btnX.setBounds(340, 13, 33, 33);
		
		btnupdate = new JButton();
		btnupdate.setBorderPainted(false);
		btnupdate.setContentAreaFilled(false);
		btnupdate.setBounds(88, 230, 91, 54);
	
		btnCancle = new JButton();
		btnCancle.setBorderPainted(false);
		btnCancle.setContentAreaFilled(false);
		btnCancle.setBounds(200, 230, 91, 54);
		
	    tfId = new JTextField();
	    tfId.setEditable(false);
	    tfId.setBackground(new Color(255, 255, 255));
	    tfId.setBounds(135, 90, 190, 27);
        tfId.setFont(font);
        tfId.setBorder(null);
        tfId.setText(owner.getId());
        
		
	    tfPw = new JTextField();
	    tfPw.setBounds(135, 137, 190, 27);	    
        tfPw.setFont(font);
        tfPw.setBorder(null);
        tfPw.setText(owner.getPw());
	    
	    textNickname = new JTextField();
	    textNickname.setBounds(135, 185, 190, 27);	    
        textNickname.setFont(font);
        textNickname.setBorder(null);
        textNickname.setText(owner.getNickname());
	}
	
    private void showFrame() {
        setSize(379,292);//프레임의 크기
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
        setBackground(new Color(0,0,0,100));
    }
	
    private void addListeners() {
        btnupdate.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                if (tfPw.getText().isEmpty() || textNickname.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(UserUpdatePopup.this,
                            "수정 내역을 입력해주세요.",
                            "오류",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                	users.retouchUsers(tfId.getText(), tfPw.getText(),
                			textNickname.getText());
                	JOptionPane.showMessageDialog(
                			UserUpdatePopup.this,
                            "수정되었습니다."
                    );
            		owner.getDTmodel().setValueAt(tfPw.getText(), owner.getJtable().convertRowIndexToModel(owner.getJtable().getSelectedRow()), 2);
            		owner.getDTmodel().setValueAt(textNickname.getText(), owner.getJtable().convertRowIndexToModel(owner.getJtable().getSelectedRow()), 3);
                	dispose();
                }
        	}
    	});
    	
        btnX.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            dispose();
        	}
    	});
        btnCancle.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            dispose();
        	}
    	});
    }
}
