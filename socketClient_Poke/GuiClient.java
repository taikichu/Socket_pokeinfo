package socketClient_Poke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class GuiClient extends JFrame{
	JPanel pane1=(JPanel)getContentPane();
	ActionListener actionListener = new TextFieldAction();
	static JTextArea infoArea;
	static JTextField search;
	static JButton socketB;static JButton catchP;
	public GuiClient() {super();}
	public GuiClient(String title) {
		super( title );
		//コンテントペインを取得。
		pane1=(JPanel)getContentPane();
		pane1.setLayout(new BorderLayout());//パネルの配置をBorderLayoutに設定
		//コンテントペインに貼り付ける
		JPanel menu1=new JPanel();
		JPanel menu2=new JPanel();
		//縦並びに設定
		menu1.setLayout(new BoxLayout(menu1,BoxLayout.Y_AXIS));
		menu1.setPreferredSize(new Dimension(400,0));//サイズを横400,縦0に設定。他のパネルとの比率でサイズが決まるのでこの設定の限りではない。縦はBorderLayoutより引き延ばされるため設定していない
		//縦並びに設定
		menu2.setLayout(new BoxLayout(menu2,BoxLayout.Y_AXIS));
		menu2.setPreferredSize(new Dimension(400,0));//サイズを横400,縦0に設定。他のパネルとの比率でサイズが決まるのでこの設定の限りではない。
		//検索バー設置
		search=new JTextField();
		//Enterキーを押したら反応する
		search.addActionListener( actionListener );
		//検索バーにタイトルを設定
		search.setBorder(new TitledBorder( "検索バー（カタカナでポケモンの名前を入力して下さい）" ) );
		//検索履歴のテキストエリアを設置
		infoArea=new JTextArea();
		infoArea.setPreferredSize(new Dimension(400,0));//サイズを横400,縦0に設定。他のパネルとの比率でサイズが決まるのでこの設定の限りではない。縦はBorderLayoutより引き延ばされるため設定していない
		//直接書き込めないようにしている
		infoArea.setEnabled(false);
		
		//ソケット通信するためのボタンを設置
		socketB=new JButton(new RactionSo());
		socketB.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));//設置するパネルぎりぎりまで
		//捕獲率計算するためのボタンを設置
		catchP=new JButton(new RactionC());
		catchP.setMaximumSize(new Dimension(Short.MAX_VALUE,Short.MAX_VALUE));//設置するパネルぎりぎりまで
		//パネルにボタンを貼り付ける
		menu1.add(catchP);
		menu2.add(socketB);
		Resettext();//ボタンにソケット通信未接続であると表記
		//表示するパネルに定義、設定したmenu1,infoArea,search,menu2を貼り付ける
		pane1.add(menu1,BorderLayout.EAST);
		pane1.add(infoArea,BorderLayout.WEST);
		pane1.add(search,BorderLayout.NORTH);
		pane1.add(menu2,BorderLayout.CENTER);
	}
	//ソケット通信接続済みであると返す
	public void Rettext() {
		socketB.setText("ソケット通信（接続済み）");
		catchP.setText("捕獲率（接続済み）");
	}
	//ソケット通信未接続であると返す
	public void Resettext() {
		socketB.setText("ソケット通信（未接続）");
		catchP.setText("捕獲率（未接続）");
	}
	//テキストエリアに入力された文字列を追加する
	public void RetArea(String elements) {
		infoArea.append(elements+"\n");
	}
	//検索バーにEnterが押されたときに反応する
	class TextFieldAction implements ActionListener {
	    public void actionPerformed( ActionEvent e ){
	    	String name=search.getText();//検索バーに入力された文字をnameに格納
	    	search.setText(null);//検索バーを初期化
	    	RactionSo actionA=new RactionSo();//RactionSoのインスタンスを生成
	      actionA.RactionSe(name);//RactionSeを引数nameを用いて呼び出す
	    }
	  }
}
