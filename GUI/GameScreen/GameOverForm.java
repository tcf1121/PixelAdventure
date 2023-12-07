package GUI.GameScreen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import Back.Datarelated.Ability;
import Back.Datarelated.CharDataSet;
import Back.Datarelated.UserDataSet;

import GUI.MainScreen.WaitForm;

public class GameOverForm extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private WaitForm owner;
	private Ability Now_Ability = new Ability("" , 0, 0, 0, 0);
    private JLabel lblCharater;
    private JLabel lblLv;
    private JButton[] btnNext = new JButton[2];
	private JProgressBar Expbar;
    private UserDataSet users = new UserDataSet();
    private int[] exp = new int[2];
    private ImageIcon Charicon;
    private int bf_exp;
    private CharDataSet lookab = new CharDataSet();
    
	public GameOverForm(WaitForm owner, int now_exp, int clear) {
	    this.owner = owner;
	    exp = users.see_exp(owner.getId());
		Now_Ability = lookab.getNAbility(owner.getId());
		bf_exp = now_exp;
		if(clear == 0) {icon = new ImageIcon(GameOverForm.class.getResource("/image/background/overbg.png"));}
		else {icon = new ImageIcon(GameOverForm.class.getResource("/image/background/clearbg.png"));}
		
		
		img_class(Now_Ability.getcl(), clear);
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
		for(int i=0;i<btnNext.length;i++) {
			bg.add(btnNext[i]);
		}
		
		bg.add(Expbar);
		bg.add(lblLv);
	    bg.add(lblCharater);
	    scrollPane = new JScrollPane(bg);
	    setContentPane(scrollPane);
	    
	    addListeners();
		showFrame();
	}
	
	private void init() {
		ImageIcon conicon = new ImageIcon(GameOverForm.class.getResource("/image/ui/continue.png"));
		ImageIcon overicon = new ImageIcon(GameOverForm.class.getResource("/image/ui/gohome.png"));
		
		Color YColor = new Color(255, 217, 102);
		
		lblCharater = new JLabel(Charicon);
		lblCharater.setBounds(313, 115, 200, 200);
		lblLv = new JLabel(String.valueOf(users.now_Lv(owner.getId())));
		lblLv.setForeground(YColor);
		lblLv.setBounds(375, 370, 30, 30);
		lblLv.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 28));
		lblLv.setHorizontalAlignment(JLabel.CENTER);
		/*btncheck.setBorderPainted(false);
		btncheck.setContentAreaFilled(false);*/
		for(int i=0;i<btnNext.length;i++) {
			if(i==1) {
				btnNext[i] = new JButton(overicon);
			}
			else {
				btnNext[i] = new JButton(conicon);
			}
			btnNext[i].setBounds(255, 465, 255, 66);
			btnNext[i].setBorderPainted(false);
			btnNext[i].setContentAreaFilled(false);
		}
		btnNext[1].setVisible(false);
		
		Expbar = new JProgressBar(0, exp[1]);
		Expbar.setValue(bf_exp);
		Expbar.setStringPainted(true);
		Expbar.setForeground(Color.YELLOW);
		Expbar.setBackground(Color.black);
		Expbar.setBounds(90, 414, 600, 40);

	}
	
	private void exp_goingup(java.awt.event.ActionEvent evt) {
		final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i=bf_exp;i<=exp[0];i++) {
                	Expbar.setValue(i);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {}
                }
                return null;
            }
        };
        
		if(exp[0] >= exp[1]) {
			worker.execute();
	        users.UserLvUp(owner.getId());
			exp = users.see_exp(owner.getId());
			Expbar.setMaximum(exp[1]);
			lblLv.setText(String.valueOf(users.now_Lv(owner.getId())));
			bf_exp = 0;
		}
		worker.execute();
		 
	}
	private void img_class(String class_no, int clear) {
		if(class_no.equals("전사")) {
			if(clear == 1) {Charicon = new ImageIcon(GameOverForm.class.getResource("/image/character/warrior_clear.png"));}
			else {Charicon = new ImageIcon(GameOverForm.class.getResource("/image/character/warrior_over.png"));}
		}
		else if(class_no.equals("마법사")) {
			if(clear == 1) {Charicon = new ImageIcon(GameOverForm.class.getResource("/image/character/wizard_clear.png"));}
			else {Charicon = new ImageIcon(GameOverForm.class.getResource("/image/character/wizard_over.png"));}
		}
		else if(class_no.equals("도적")) {
			if(clear == 1) {Charicon = new ImageIcon(GameOverForm.class.getResource("/image/character/rouge_clear.png"));}
			else {Charicon = new ImageIcon(GameOverForm.class.getResource("/image/character/rouge_over.png"));}
		}
	}
    private void showFrame() {
        setTitle("픽셀 어드벤쳐");//타이틀
        setSize(800,600);//프레임의 크기
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
	
    private void addListeners() {

        btnNext[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	exp_goingup(e);
                btnNext[0].setVisible(false);                
                btnNext[1].setVisible(true);
            }
        });
        btnNext[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //setVisible(false);
                owner.setVisible(true);
            }
        });
        
    }
}
