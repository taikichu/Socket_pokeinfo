package socketServer_Poke;
public class HPclass {
	public HPclass() {}
	//HPを計算するメソッドを定義
	public int RetHP(int S,int K,int D,int level) {
		return((S*2)+K+(D/4))*(level/100)+(10+level);
	}
}
