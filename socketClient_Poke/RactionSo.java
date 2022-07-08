package socketClient_Poke;

import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RactionSo extends AbstractAction{
	final String localHost="localHost";
	static int count1;
	static Socket socket;
	public void actionPerformed(ActionEvent e) {
		GuiClient gui=new GuiClient();
		JPanel pane=new JPanel();
        try {
        	socket =new Socket(localHost,1107);
        	count1++;
        	if(count1%2==1) {//count1をカウントするのが奇数回目の時に接続を許可
        		 JOptionPane.showMessageDialog( pane, "サーバーに接続しました！" ); // ダイアログの表示
        		 gui.Rettext();//接続されたとボタンに表示
        		
        	}
        	else {//count1をカウントするのが偶数回目の時に接続を拒否
        		JOptionPane.showMessageDialog( pane, "サーバーが切断されました" ); // ダイアログの表示
        		gui.Resettext();//接続が切断されたとボタンに表示
        		socket.close();//ソケット通信を切断
        		System.exit(0);//プログラムを終了する
        	}
        }catch(Exception e1) {
        	JOptionPane.showMessageDialog( pane, "接続できませんでした" ); // ダイアログの表示
        }
	}
		public void RactionSe(String name){
			JPanel pane=new JPanel();
			GuiClient gui=new GuiClient();
				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//ソケット通信で送信するための器を定義
				out.println(name+"search");//サーバーにname+"search"を送信
				gui.RetArea("検索バーから　"+name+"　が入力されました");//テキストエリアに入力された文字列を表示
		        }catch(Exception e1) {
		        	JOptionPane.showMessageDialog( pane, "エラーが発生しました。接続を切断します" ); // ダイアログの表示
		        	System.exit(-1);//プログラムを終了する。
		        }
		}
		public Socket Rsocket() {
			return socket;//ソケット通信の口を返す
		}
}
class RactionC extends AbstractAction{
	public void actionPerformed(ActionEvent e) {
		JPanel pane=new JPanel();
		GuiClient gui=new GuiClient();
		RactionSo socket=new RactionSo();
		Random random=new Random();
		// ボールの選択肢を準備	
		String[] choice = { "モンスターボール", "スーパーボール", "ハイパーボール", "マスターボール", "サファリボール", "パークボール", 
				"コンペボール", "ヘビーボール","ラブラブボール","フレンドボール","ネットボール", "ネストボール","リピートボール",
				"プレミアボール","ゴージャスボール","ヒールボール","クイックボール"}; 
		//ボールを選択するダイアログを表示
	      Object ball = JOptionPane.showInputDialog( pane, "使用するボールを選択して下さい", "捕獲率計算",
                  JOptionPane.PLAIN_MESSAGE, new ImageIcon( "pokeball2\\"+random.nextInt(26)+".png" ),
                  choice, choice[0] );
	      if(ball!=null) {
	    	  //ポケモンを選択するダイアログを表示
	    	  Object retpoke = JOptionPane.showInputDialog( pane, "捕まえたいポケモンを入力して下さい","捕獲率計算",
	    			  JOptionPane.PLAIN_MESSAGE,new ImageIcon( "pokeball2\\"+ball.toString()+".png"),null,"");
	    	  if(retpoke!=null) {
	    		  try {
						PrintWriter out = new PrintWriter((socket.Rsocket()).getOutputStream(), true);//ソケット通信で送信するための器を定義
						out.println(retpoke.toString()+"catch");//サーバーにretpoke+"catch"を送信
						out.println(ball.toString());//サーバーにballを送信
						gui.RetArea("捕獲率から　"+ball.toString()+"　と　"+retpoke.toString()+"　が入力されました");//テキストエリアに入力された文字列を表示
			        }catch(Exception e1) {
			        	JOptionPane.showMessageDialog( pane, "エラーが発生しました。接続を切断します" ); // ダイアログの表示
			        	System.exit(-1);//プログラムを終了する
			        }
	    	  }
	    	  else JOptionPane.showMessageDialog( pane, "取り消しが選択されました" ); // ダイアログの表示
	      }
	      else JOptionPane.showMessageDialog( pane, "取り消しが選択されました" ); // ダイアログの表示
	}
}