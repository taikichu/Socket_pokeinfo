package socketClient_Poke;

import javax.swing.JFrame;
//スタティックメインを配置
public class MainClient {
	public static void main(String[] args) {
		GuiClient w=new GuiClient("メインメニュー");//メインメニューのインスタンスを生成。名前を付ける。
		w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );//メインメニューのバツが押されたらクライアントプログラムを終了する
		w.setExtendedState(JFrame.MAXIMIZED_BOTH);//パネルを画面いっぱいに表示
		w.setLocation(250,250);//x軸250,y軸250の場所に設定。画面を最大に設定しているので小さくしたときの座標
		w.setSize( 600, 600 );//サイズを縦横600ピクセルに設定
		w.setVisible( true );//定義したパネルを表示する
	}
}
