package socketServer_Poke;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class MainServer {
	//new Serverpoke();
public static final int PORT = 1107;
 public static void main(String[] args) {
	ServerSocket serverSocket = null;
	Socket socket = null;
	ObjectMapper mapper = new ObjectMapper();
	JPanel paneError=new JPanel();
	int no=-3;
	int no2=0;
	try {
		serverSocket = new ServerSocket(PORT);//ServerSocket型のserverSocketをPORT=1107でインスタンス生成
		System.out.println("Server2が起動しました(port="+serverSocket.getLocalPort() + ")");//printlnでサーバー起動を知らせる
		socket = serverSocket.accept();//クライアントからの接続を受け入れる
		JsonNode search = mapper.readTree(new File("pokemon.json"));//JsonNode型のsearchを定義。ここからポケモンの情報をサーチする
		String line=null;
		int catchR=0;
		System.out.println("接続されました" + socket.getRemoteSocketAddress() );//printlnで接続したことを知らせる
				BufferedReader in = 
				new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));//クライアントから送られてきた文字列を受け取る器を定義
				//clientから送られてきたstringを読み込んで選別する
				while((line=in.readLine())!=null) {
					if(line.contains("search")) {	//検索バーから送られてきたstringを受け入れる
						line= line.replaceAll("search","");//送られてきた文字列の中のsearchを削除
						for(int i=0;i<898;i++) {						
							if(line.equals(search.get(i).get("名前").asText())) {
								no=search.get(i).get("全国No.").asInt();//全国ナンバーをnoに代入
			            		break;
							}
						}
					}
					else if(line.contains("catch")){	//捕獲率計算から送られてきたstringを受け入れる
						line= line.replaceAll("catch","");//送られてきた文字列の中のcatchを削除
						for(int i=0;i<898;i++) {						
							if(line.equals(search.get(i).get("名前").asText())) {
								catchR=Integer.parseInt(search.get(i).get("その他のデータ").get("ゲットしやすさ").asText());//ゲットしやすさ（捕捉率）をcatchRに代入
								no2=search.get(i).get("全国No.").asInt();//全国ナンバーをno2に代入
								no=-1;//noに-1を代入
			            		break;
							}
						}
					}
					if(0<no&&no<899) {//searchから送られてきたポケモンを調べる
						System.out.println("入力されたポケモンは？-----→　 " + line);
						Search info=new Search();
						String[] a=new String[info.Pokeinfo(no).length];a=info.Pokeinfo(no);
						String[] b=new String[info.RetBvalue().length];b=info.RetBvalue();
						String[] c=new String[info.RetCvalue().length];c=info.RetCvalue();
						//searchクラスで抜き出した情報を見やすくするために整理する
						for(int i=1;i<(a.length);i++) {
							if(b[i]==null) break;
							else {
								if(b[i].equals(b[i-1])) {
									if(c[i]==null)a[i]="  "+a[i];
									else a[i]="  "+c[i]+" "+a[i];
								}
								else {
									if(c[i]==null)a[i]=b[i]+" "+a[i];
									else a[i]=b[i]+" "+c[i]+" "+a[i];
									}
							}
						}
						Object[] msg = { a }; // メッセージの準備
					      JOptionPane.showMessageDialog( paneError, msg,line+"の情報",
					    		  JOptionPane.PLAIN_MESSAGE,new ImageIcon( "pokemonfile\\image"+no+".png" )); // ポケモンの情報をダイアログの表示
					}
					else if(no==-1) {//catchから送られてきたポケモンの捕獲率を調べる
						String ball=in.readLine();//設定したボールをクライアントから受け取る
						Catch C=new Catch();
						String[] list=C.CatchR(catchR, ball, (no2-1));	//CatchRにボールの捕獲補正とボール名とポケモンの図鑑番号-1を渡す
						double value=C.Retan();
						if(value>=255) {
							String[] showlist={list[0],list[1],list[3],list[5],list[2],list[7],list[8],list[9],list[10],C.line()};//状態異常無しで捕獲率100%になるときなので状態異常無し時のしたに捕獲率を表示
							JOptionPane.showMessageDialog( paneError, showlist,"捕獲率",JOptionPane.PLAIN_MESSAGE,new ImageIcon( "pokeball2\\"+ball+".png")); // ダイアログの表示
						}
						else if(value*1.5>=255) {
							String[] showlist={list[0],list[3],list[1],list[5],list[2],list[7],list[8],list[9],list[10],C.line()};//軽い状態異常で捕獲率100%になるときなので状態異常無し時のしたに捕獲率を表示
							JOptionPane.showMessageDialog( paneError, showlist,"捕獲率",JOptionPane.PLAIN_MESSAGE,new ImageIcon( "pokeball2\\"+ball+".png")); // ダイアログの表示
						}
						else if(value*2.5>=255) {
							String[] showlist={list[0],list[3],list[5],list[1],list[2],list[7],list[8],list[9],list[10],C.line()};//重い状態異常で捕獲率100%になるときなので状態異常無し時のしたに捕獲率を表示
							JOptionPane.showMessageDialog( paneError, showlist,"捕獲率",JOptionPane.PLAIN_MESSAGE,new ImageIcon( "pokeball2\\"+ball+".png")); // ダイアログの表示
						}
						else {
							String[] showlist={list[0],list[3],list[5],list[2],list[7],list[8],list[9],list[10],C.line()};//捕獲率100%を表示せずにそれ以外を表示
							JOptionPane.showMessageDialog( paneError, showlist,"捕獲率",JOptionPane.PLAIN_MESSAGE,new ImageIcon( "pokeball2\\"+ball+".png")); // ダイアログの表示
						}
					}
					else if(no==-2);
					else {
						JOptionPane.showMessageDialog( paneError, line+"がポケモンの名前として認識されませんでした。もう一度入力してください"); // ダイアログの表示
					}
				}
	}catch (IOException e1) {
		e1.printStackTrace();
		} 
}
}