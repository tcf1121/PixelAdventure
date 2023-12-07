package GUI.BattleScreen.Popup;

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
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import GUI.BattleScreen.BattleForm;





public class battleresultPopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private JButton btnok;
	private JLabel lblbg;
	private JLabel lblresult;
	private BattleForm owner;
	private String win;
	public battleresultPopup(BattleForm owner, int win){ 
	    icon = new ImageIcon(battleresultPopup.class.getResource("/image/popupbg/battleresultbg.png"));
		this.owner = owner;
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
	    if(win == 1) {this.win = "승리";}
	    else {this.win = "패배";}
	    bg.setLayout(null);
	    init();
		
	    scrollPane = new JScrollPane(bg);
	    

	    bg.add(btnok);
	    bg.add(lblresult);
	    bg.add(lblbg);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {		
		lblbg = new JLabel();
	    lblbg.setIcon(icon);
	    lblbg.setBounds(0, 0, 296, 179);
		lblresult = new JLabel(win);
		lblresult.setHorizontalAlignment(JLabel.CENTER);
		lblresult.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		lblresult.setForeground(new Color(255, 255, 255));
	    lblresult.setBounds(56, 61, 183, 59);

	    btnok = new JButton();
	    btnok.setBorderPainted(false);
	    btnok.setContentAreaFilled(false);
	    btnok.setBounds(104, 129, 87, 38);
	}
	
    private void showFrame() {
        setSize(296, 179);//프레임의 크기
        ;
        setUndecorated(true);
        Point p = new Point();
        p.setLocation(owner.getjf().getLocation().getX()+
        		(owner.getjf().getSize().getWidth()-getSize().getWidth())/2,
        		owner.getjf().getLocation().getY()+
        		(owner.getjf().getSize().getHeight()-getSize().getHeight())/2);
        setLocation(p);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
	
    private void addListeners() {

        btnok.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		dispose();
        		owner.getjf().dispose();
        		owner.getowner().setVisible(true);
        		owner.interrupt();
        	}
    	});
    }
}
