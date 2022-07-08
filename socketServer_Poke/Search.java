package socketServer_Poke;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Search{
	static String[] b;
	static String[] c;
	public String[] Pokeinfo(int no) {
		no--;
		int i=0;

		String[] a=new String[30];//検索した値を格納
		b=new String[30];//jsonファイル内の分岐の一つ目を格納
		c=new String[30];//jsonファイル内の分岐の二つ目を格納
		try {
            ObjectMapper mapper = new ObjectMapper();//Jsonファイルを使うためのObjectMapperを定義
            JsonNode search = mapper.readTree(new File("pokemon.json"));//JsonNode型でpokemon.jsonを読み込み、使えるようにする
            //配列a,b,cを初期化
            for(int i2=0;i2<a.length;i2++) a[i2]=null;
            for(int i2=0;i2<b.length;i2++) b[i2]=null;
            for(int i2=0;i2<c.length;i2++) c[i2]=null;
            //配列a[i]番目に各b[i]、もしくはその先のc[i]の値を格納
    		a[++i]=search.get(no).get(b[i]="名前").asText();//a[i]にi番目の"名前"に該当する値を代入する
    		a[++i]=search.get(no).get(b[i]="種族").asText();
    		a[++i]=search.get(no).get(b[i]="全国No.").asText();
    		a[++i]=search.get(no).get(b[i]="高さ").asText();
    		a[++i]=search.get(no).get(b[i]="重さ").get(0).asText();
    		a[++i]=search.get(no).get(b[i]="タイプ").get(0).asText();
    		if(search.get(no).get("タイプ").size()>=2) a[++i]=search.get(no).get(b[i]="タイプ").get(1).asText();
    		a[++i]=search.get(no).get(b[i]="英語名").asText();
    		a[++i]=search.get(no).get(b[i]="進化の流れ").asText();
    		a[++i]=search.get(no).get(b[i]="種族値").get(c[i]="HP").asText();
    		a[++i]=search.get(no).get(b[i]="種族値").get(c[i]="こうげき").asText();
    		a[++i]=search.get(no).get(b[i]="種族値").get(c[i]="ぼうぎょ").asText();
    		a[++i]=search.get(no).get(b[i]="種族値").get(c[i]="とくこう").asText();
    		a[++i]=search.get(no).get(b[i]="種族値").get(c[i]="とくぼう").asText();
    		a[++i]=search.get(no).get(b[i]="種族値").get(c[i]="すばやさ").asText();//6
    		a[++i]=search.get(no).get(b[i]="その他のデータ").get(c[i]="ゲットしやすさ").asText();
    		a[++i]=search.get(no).get(b[i]="その他のデータ").get(c[i]="初期なつき度").asText();
    		a[++i]=search.get(no).get(b[i]="その他のデータ").get(c[i]="基礎経験値").asText();
    		a[++i]=search.get(no).get(b[i]="その他のデータ").get(c[i]="経験値タイプ").asText();
    		a[++i]=search.get(no).get(b[i]="その他のデータ").get(c[i]="色").asText();
    		a[++i]=search.get(no).get(b[i]="その他のデータ").get(c[i]="カテゴリー").asText();
    		a[++i]=search.get(no).get(b[i]="性別とタマゴ").get(c[i]="性別").asText();
    		a[++i]=search.get(no).get(b[i]="性別とタマゴ").get(c[i]="タマゴ歩数").asText();          
    		a[++i]=search.get(no).get(b[i]="性別とタマゴ").get(c[i]="タマゴグループ").asText();
    		a[++i]=(search.get(no).get(b[i]="特性")).toString();
    		a[++i]=(search.get(no).get(b[i]="隠れ特性")).toString();
    		
    		return a;//作製したString[]を返す
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;//エラーを送るためにnullをかえす
        }
	}
	public String[] RetBvalue() {
		return b;//分岐の一つめを返す
	}
	public String[] RetCvalue() {
		return c;//分岐の二つ目を返す
	}
}
