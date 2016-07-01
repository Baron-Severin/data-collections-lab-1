import java.util.Comparator;

public class MyComparator implements Comparator<String> {

	@Override
	public int compare(String arg0, String arg1) {

		int indexOf = arg0.indexOf("-");
		String substring = arg0.substring(0, indexOf-1);
		int indexOf1 = arg1.indexOf("-");
		String substring1 = arg1.substring(0, indexOf1-1);
		return Integer.valueOf(substring) - Integer.valueOf(substring1);
	}

}