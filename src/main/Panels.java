package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;

/*
 * ベースパネル
 * */
abstract class BasePanel extends JPanel {
	abstract protected void prepareComponent();
}

/*
 * タイトル画面
 * */
class TitlePanel extends BasePanel {
	private Font font;
	private JLabel label;
	private JButton buttonScreenTransitionToAdventure;
	private JButton buttonScreenTransitionToBattle;
	private JButton buttonScreenTransitionToItem;
	private TitleButtonListener titleButtonListener;

	/* コンストラクタ */
	public TitlePanel() {
		// フォントの設定
		font = new Font("Century", Font.PLAIN, 30);
		// テキストラベルの作成
		label = new JLabel();
		// ボタンの作成
		buttonScreenTransitionToAdventure = new JButton("Adventureへ画面遷移");
		buttonScreenTransitionToBattle = new JButton("Battleへ画面遷移");
		buttonScreenTransitionToItem = new JButton("Itemへ画面遷移");
		// ボタンリスナーの登録
		titleButtonListener = new TitleButtonListener();
		new TitleKeyAdapter(this);
	}

	@Override
	protected void prepareComponent() {
		// パネルの背景色の設定
		super.setBackground(Color.black);

		// テキストラベルの更新
		label.setText("Title");
		// フォントの反映
		label.setFont(this.font);
		// フォントの色指定
		label.setForeground(Color.red);
		// 水平位置・垂直位置の設定
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		// 背景色の設定
		label.setOpaque(true);
		label.setBackground(Color.gray);

		// Tabキーによる選択の有効化
		buttonScreenTransitionToAdventure.setFocusable(true);
		buttonScreenTransitionToBattle.setFocusable(true);
		buttonScreenTransitionToItem.setFocusable(true);
		// ボタンにリスナー登録
		buttonScreenTransitionToAdventure.addActionListener(titleButtonListener);
		buttonScreenTransitionToBattle.addActionListener(titleButtonListener);
		buttonScreenTransitionToItem.addActionListener(titleButtonListener);

		// パネルに各コンポーネントを追加
		super.setLayout(new BorderLayout());
		super.add(this.label, BorderLayout.NORTH);
		super.add(this.buttonScreenTransitionToAdventure, BorderLayout.WEST);
		super.add(this.buttonScreenTransitionToBattle, BorderLayout.CENTER);
		super.add(this.buttonScreenTransitionToItem, BorderLayout.EAST);
	}

	/* ボタンリスナーの設定 */
	private class TitleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonScreenTransitionToAdventure) {
				Main.window.setFrontScreenAndFocus(ScreenMode.ADVENTURE);
			}
			if (e.getSource() == buttonScreenTransitionToBattle) {
				Main.window.setFrontScreenAndFocus(ScreenMode.BATTLE);
			}
			if (e.getSource() == buttonScreenTransitionToItem) {
				Main.window.setFrontScreenAndFocus(ScreenMode.ITEM);
			}
		}
	}

	/* キーリスナーの設定 */
	private class TitleKeyAdapter extends KeyAdapter {
		/* コンストラクタ */
		private TitleKeyAdapter(TitlePanel p) {
			super();
			p.addKeyListener(this);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}
		}
	}
}

/*
 * 冒険画面
 * */
class AdventurePanel extends BasePanel {
	private Font font;
	private JLabel label;

	/* コンストラクタ */
	public AdventurePanel() {
		// フォントの設定
		font = new Font("Century", Font.PLAIN, 30);
		// テキストラベルの作成
		label = new JLabel();
		// キーリスナーの登録
		new AdventureKeyAdapter(this);
	}

	@Override
	protected void prepareComponent() {
		// パネルの背景色の設定
		super.setBackground(Color.blue);

		// テキストラベルの更新
		label.setText("Adventure");
		// フォントの反映
		label.setFont(this.font);
		// フォントの色指定
		label.setForeground(Color.white);
		label.setHorizontalAlignment(JLabel.CENTER);

		// パネルに各コンポーネントを追加
		super.setLayout(new BorderLayout());
		super.add(this.label, BorderLayout.CENTER);
	}

	/* キーリスナーの設定 */
	private class AdventureKeyAdapter extends KeyAdapter {
		AdventurePanel panel;

		/* コンストラクタ */
		private AdventureKeyAdapter(AdventurePanel p) {
			super();
			panel = p;
			panel.addKeyListener(this);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				Main.window.setFrontScreenAndFocus(ScreenMode.TITLE);
				break;
			}
		}
	}
}

/*
 * バトル画面
 * */
class BattlePanel extends BasePanel {
	private Font font;
	private JLabel label;
	private CharacterInfoPanel characterInfoPanel1;
	private CharacterInfoPanel characterInfoPanel2;

	/* コンストラクタ */
	public BattlePanel() {
		// フォントの設定
		font = new Font("メイリオ", Font.PLAIN, 15);
		// テキストラベルの作成
		label = new JLabel();
		characterInfoPanel1 = new CharacterInfoPanel(new Character("ピカチュウ", 150));
		characterInfoPanel2 = new CharacterInfoPanel(new Character("イーブイ", 130));
		// マウスリスナーの登録
		new BattleMouseAdapter(this);
	}

	@Override
	protected void prepareComponent() {
		// パネルの背景色の設定
		super.setBackground(Color.white);

		// テキストラベルの更新
		label.setText("【Battle】");
		// フォントの反映
		label.setFont(this.font);
		// フォントの色指定
		label.setForeground(Color.black);
		// 枠線の設定
		label.setBorder(new LineBorder(Color.black, 3, false));

		// パネルに各コンポーネントを追加
		super.setLayout(null);
		label.setBounds(0, 400, 500, 100);
		super.add(label);
		// 別パネルの追加
		characterInfoPanel1.prepareComponent();
		characterInfoPanel1.setBounds(10, 10, 200, 50);
		super.add(characterInfoPanel1);
		characterInfoPanel2.prepareComponent();
		characterInfoPanel2.setBounds(290, 340, 200, 50);
		super.add(characterInfoPanel2);
	}

	/* マウス・キーリスナーの設定 */
	private class BattleMouseAdapter extends MouseKeyAdapter {
		BattlePanel panel;

		/* コンストラクタ */
		private BattleMouseAdapter(BattlePanel p) {
			super();
			panel = p;
			panel.addMouseListener(this);
			panel.addKeyListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				characterInfoPanel1.hpBar.plus(45);
				break;
			case MouseEvent.BUTTON3:
				characterInfoPanel1.hpBar.minus(45);
				break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				Main.window.setFrontScreenAndFocus(ScreenMode.TITLE);
				break;
			}
		}
	}
}

/*
 * メンバ変数のキャラクター情報を表示するパネル
 * */
class CharacterInfoPanel extends BasePanel {
	private Font font;
	private JLabel label;
	protected Character character;
	protected CharacterInfoProgressBar hpBar;

	/* コンストラクタ */
	public CharacterInfoPanel(Character character) {
		// フォントの設定
		font = new Font("メイリオ", Font.PLAIN, 15);
		// テキストラベルの作成
		label = new JLabel();
		// キャラクターの生成
		this.character = character;
	}

	@Override
	protected void prepareComponent() {
		// パネルの背景色の設定
		super.setBackground(Color.white);

		// テキストラベルの更新
		label.setText(character.getName());
		// フォントの反映
		label.setFont(this.font);
		// フォントの色指定
		label.setForeground(Color.black);

		// HPバーの作成
		hpBar = new CharacterInfoProgressBar();

		// パネルに各コンポーネントを追加
		super.setLayout(new BorderLayout());
		super.add(this.label, BorderLayout.CENTER);
		super.add(hpBar, BorderLayout.PAGE_END);
	}

	/* プログレスバーの設定 */
	protected class CharacterInfoProgressBar extends JProgressBar implements ActionListener {
		int displayValue;
		ProgressBarMode mode;
		Timer timer;

		private enum ProgressBarMode {
			WAITING, DECREASING, INCREASING
		}

		/* コンストラクタ */
		private CharacterInfoProgressBar() {
			displayValue = character.getHP();
			mode = ProgressBarMode.WAITING;
			// 色の設定
			this.updateBarColor();
			super.setBackground(Color.white);
			// 文字の色の設定
			super.setUI(new BasicProgressBarUI() {
				@Override
				protected Color getSelectionForeground() {
					return Color.black;
				}

				@Override
				protected Color getSelectionBackground() {
					return Color.gray;
				}
			});

			super.setValue(this.displayValue * 100 / character.getMaxHP()); //パーセントを引数に入力
			this.updateText();
			super.setStringPainted(true); // テキストの表示
			timer = new Timer(10, this);
		}

		/* 文字列の更新 */
		private void updateText() {
			super.setString(this.displayValue + "/" + character.getMaxHP());
		}

		/* バーの色の更新 */
		private void updateBarColor() {
			if (character.getMaxHP() / 2 < this.displayValue) {
				super.setForeground(Color.green);
			} else if (character.getMaxHP() / 10 < this.displayValue) {
				super.setForeground(Color.orange);
			} else {
				super.setForeground(Color.red);
			}
		}

		/* 値を減少 */
		protected void minus(int num) {
			this.displayValue = character.getHP();
			character.damage(num);
			this.mode = ProgressBarMode.DECREASING;
			this.timer.start();
		}

		private void minusOne() {
			this.displayValue -= 1;
			super.setValue(this.displayValue * 100 / character.getMaxHP());
			this.updateText();
			this.updateBarColor();
		}

		/* 値を増加 */
		protected void plus(int num) {
			this.displayValue = character.getHP();
			character.recovery(num);
			this.mode = ProgressBarMode.INCREASING;
			this.timer.start();
		}

		private void plusOne() {
			this.displayValue += 1;
			super.setValue(this.displayValue * 100 / character.getMaxHP());
			this.updateText();
			this.updateBarColor();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (this.mode) {
			case WAITING:
				break;
			case DECREASING:
				if (this.displayValue > character.getHP()) {
					this.minusOne();
				} else {
					this.timer.stop();
					this.mode = ProgressBarMode.WAITING;
				}
				break;
			case INCREASING:
				if (this.displayValue < character.getHP()) {
					this.plusOne();
				} else {
					this.timer.stop();
					this.mode = ProgressBarMode.WAITING;
				}
				break;
			}
		}
	}
}

/*
 * アイテム画面
 * */
class ItemPanel extends BasePanel {
	private Font font;
	private JLabel label;

	/* コンストラクタ */
	public ItemPanel() {
		// フォントの設定
		font = new Font("Century", Font.PLAIN, 30);
		// テキストラベルの作成
		label = new JLabel();
		// マウスリスナーの登録
		new ItemMouseAdapter(this);
	}

	@Override
	protected void prepareComponent() {
		// パネルの背景色の設定
		super.setBackground(Color.yellow);

		// テキストラベルの更新
		label.setText("Item");
		// フォントの反映
		label.setFont(this.font);
		// フォントの色指定
		label.setForeground(Color.black);
		label.setHorizontalAlignment(JLabel.CENTER);

		// パネルに各コンポーネントを追加
		super.setLayout(new BorderLayout());
		super.add(this.label, BorderLayout.CENTER);
	}

	/* マウスリスナーの設定 */
	private class ItemMouseAdapter extends MouseAdapter {
		ItemPanel panel;

		/* コンストラクタ */
		private ItemMouseAdapter(ItemPanel p) {
			super();
			panel = p;
			panel.addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON3:
				Main.window.setFrontScreenAndFocus(ScreenMode.TITLE);
				break;
			}
		}
	}
}