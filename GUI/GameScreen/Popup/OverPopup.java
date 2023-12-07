package GUI.GameScreen.Popup;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GUI.GameScreen.GameOverForm;
import GUI.MainScreen.WaitForm;
import javax.swing.JLabel;



public class OverPopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private JButton btnok;
	private JLabel lblbg;
	private WaitForm owner;
	private int exp;
	public OverPopup(WaitForm owner,int exp, int clear) {
	    if (clear == 1) {
	    	icon = new ImageIcon(OverPopup.class.getResource("/image/popupbg/gameclearpu.png"));
	    }else {
	    	icon = new ImageIcon(OverPopup.class.getResource("/image/popupbg/gameoverpu.png"));
	    }
		this.owner = owner;
		this.exp = exp;
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
	    
	    lblbg.add(btnok);
		
	    scrollPane = new JScrollPane(bg);
	    
	    
	    bg.add(lblbg);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {		
		btnok = new JButton();
		btnok.setBorderPainted(false);
		btnok.setContentAreaFilled(false);
		btnok.setBounds(105, 69, 87, 38);
		lblbg = new JLabel();
	    lblbg.setIcon(icon);
	    lblbg.setBounds(0, 0, 296, 128);
	}
	
    private void showFrame() {
        setSize(298, 130);//프레임의 크기
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
        btnok.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        	owner.dispose();
            dispose();
            new GameOverForm(owner, exp, 1/*보스 클리어*/);
        	}
    	});
    }
}
