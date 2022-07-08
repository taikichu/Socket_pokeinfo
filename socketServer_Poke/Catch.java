package socketServer_Poke;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Catch {
	static double an;
	static int S;
	static int K;
	static int D;
	static int level;
	public String[] CatchR(int catchr,String ball,int no) {
		double ang;
		double ag15;
		double ag25;
		String [] w=new String[12];
		HPclass hpclass=new HPclass();
		Random random=new Random();
		double percent=0.0;
		ObjectMapper mapper = new ObjectMapper();
         try {
			JsonNode search = mapper.readTree(new File("pokemon.json"));
			//該当するボールの捕獲補正をballに代入
			if(ball.equals("モンスターボール")||ball.equals("フレンドボール")||ball.equals("プレミアボール")||ball.equals("ゴージャスボール")||ball.equals("ヒールボール")) percent=1.0;
			else if(ball.equals("スーパーボール")||ball.equals("サファリボール")||ball.equals("パークボール")||ball.equals("コンペボール")) percent=1.5;
			else if(ball.equals("ハイパーボール")) percent=2.0;
			else if(ball.equals("ネットボール")||ball.equals("リピートボール")) percent=3.5;
			else if(ball.equals("クイックボール")) percent=5.0;
			else if(ball.equals("ラブラブボール")) percent=8.0;
			else if(ball.equals("マスターボール")) percent=255;
			else if(ball.equals("ヘビーボール")) {
				int weight=Integer.parseInt((search.get(no).get("重さ").asText()).replaceAll("kg",""));
				if(weight<99.9) percent=-20;
				else if(100<weight&&weight<199.9) percent=0;
				else if(200<weight&&weight<299.9) percent=20;
				else if(300<weight) percent=30;
			}
			String name=search.get(no).get("名前").asText();//該当するポケモンの名前をjsonファイルから呼び出す
			S=search.get(no).get("種族値").get("HP").asInt();//該当するポケモンのHPをjsonファイルから呼び出す
			K=random.nextInt(31);//0～31からランダムでKに代入
			D=random.nextInt(252);//0～252の中からランダムでDに代入
			level=(random.nextInt(99)+1);//1～100の中からランダムでlevelに代入
			int hp=hpclass.RetHP(S, K, D, level);//hpclassのRetHPを使ってHP計算して、代入
			//ヘビーボールを用いた時の捕獲率を計算
			if(ball.equals("ヘビーボール")) {
				an=((hp*catchr)/(hp*3))+percent;
				if(an<=0) {an=1;} 
				ang=65536/Math.pow(((255/an)),0.1875);
				ag15=65536/Math.pow(((255/an)*1.5),0.1875);
				ag25=65536/Math.pow(((255/an)*2.5),0.1875);
			}
			//マスターボールを用いた時の捕獲率を計算
			else if(ball.equals("マスターボール")) {
				an=255;
				ang=0;
				ag15=65535;
				ag25=65535;
			}
			//その他ボールを用いた時の捕獲率を計算
			else {
				an=((hp*catchr*percent)/(hp*3));
				ang=65536/Math.pow(((255/an)),0.1875);
				ag15=65536/Math.pow(((255/an)*1.5),0.1875);
				ag25=65536/Math.pow(((255/an)*2.5),0.1875);
			}
			//配列wに各場合について入力、格納
			w[0]="状態異常無し: "+name+"に"+ball+"を使用した時のff捕獲率は"+an+"です";
			w[1]="捕獲率が255以上のため100%の確率で捕獲できます\n";//状態異常の有無問わずff捕獲率100%であるとき表示する
			w[2]="捕獲率が255未満のため以下の計算式が適用され、捕獲率は"+Math.pow(ang/65536,4)+" : "+Math.pow(ang/65536,4)*100+"%\n";
			
			w[3]="どく、まひ、やけど: "+name+"に"+ball+"を使用した時のff捕獲率は"+an*1.5+"です。";
			w[4]="捕獲率が255未満のため以下の計算式が適用され、捕獲率は"+Math.pow(ag15/65536,4)+" : "+Math.pow(ag15/65536,4)*100+"%\n";
			
			w[5]="こおり、ねむり: "+name+"に"+ball+"を使用した時のff捕獲率は"+an*2.5+"です";
			w[6]="捕獲率が255未満のため以下の計算式が適用され、捕獲率は"+Math.pow(ag25/65536,4)+" : "+Math.pow(ag25/65536,4)*100+"%\n";
			
			w[7]="計算式： 式A=(((最大HP×3－現在HP×2)×捕捉率×捕獲補正)÷(最大HP×3))×状態異常補正";
			w[8]="式Aが255以上であるとき捕獲率は100%になる";
			w[9]="式Aが255未満であるとき : 式B=65536 / (255/式A)^0.1875";
			w[10]="0～65536を4回繰り返しすべて式Bより小さい場合捕獲成功となる";
			
			
		} catch (JsonProcessingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return w;//作製した配列wをメソッドに返す
	}
	public double Retan() {
		return an;
	}
	public String line() {
		return "今回使用した能力値→　HP："+S+"　個体値："+K+"　努力値："+D+"　レベル："+level;
	}
}
