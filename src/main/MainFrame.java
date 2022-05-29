package main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

/*
 * スクリーン状態
 * */
enum ScreenMode {
	TITLE("タイトル画面"), ADVENTURE("冒険画面"), BATTLE("バトル画面"), ITEM("アイテム画面");

	// スクリーンの情報
	protected String ScreenInfo;

	/* コンストラクタ */
	private ScreenMode(String ScreenInfo) {
		this.ScreenInfo = ScreenInfo;
	}
}

/*
 * メインフレーム
 * */
public class MainFrame extends JFrame {
	private ScreenMode screenMode;
	private ArrayList<PanelInfo> panelInfoArray;
	private CardLayout layout;

	/* コンストラクタ */
	public MainFrame(String windowName) {
		// メンバ変数の初期化
		screenMode = ScreenMode.TITLE;
		panelInfoArray = new ArrayList<>();
		layout = new CardLayout();

		// ウィンドウタイトルの設定
		super.setTitle(windowName);
		// ウィンドウ内側のサイズの指定
		super.getContentPane().setPreferredSize(new Dimension(500, 500));
		super.pack();
		// ウィンドウサイズの可変性
		super.setResizable(false);
		// ウィンドウを閉じた際に，プログラムを終了させる
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// カードを切り替えて画面の表示を行う
		super.setLayout(layout);

		this.preparePanels();
	}

	/* パネルの準備 */
	private void preparePanels() {
		// パネルの作成
		this.panelInfoArray.add(new PanelInfo(new TitlePanel(), ScreenMode.TITLE));
		this.panelInfoArray.add(new PanelInfo(new AdventurePanel(), ScreenMode.ADVENTURE));
		this.panelInfoArray.add(new PanelInfo(new BattlePanel(), ScreenMode.BATTLE));
		this.panelInfoArray.add(new PanelInfo(new ItemPanel(), ScreenMode.ITEM));
		// 各パネルのコンポーネントの準備
		for (PanelInfo panelInfo : this.panelInfoArray) {
			panelInfo.panel.prepareComponent();
		}
		// フレームにパネルの追加
		for (PanelInfo panelInfo : this.panelInfoArray) {
			super.add(panelInfo.panel, panelInfo.screenMode.ScreenInfo);
		}
	}

	/* 表示するパネルの選択 */
	public void setFrontScreenAndFocus(ScreenMode screenMode) {
		this.screenMode = screenMode;
		for (PanelInfo panelInfo : this.panelInfoArray) {
			if (panelInfo.screenMode == this.screenMode) {
				this.layout.show(this.getContentPane(), panelInfo.screenMode.ScreenInfo);
				panelInfo.panel.requestFocus();
				break;
			}
		}
	}

	/* パネルコンポーネント情報格納クラス */
	private class PanelInfo {
		private BasePanel panel;
		private ScreenMode screenMode;

		private PanelInfo(BasePanel panel, ScreenMode screenMode) {
			this.panel = panel;
			this.screenMode = screenMode;
		}
	}
}