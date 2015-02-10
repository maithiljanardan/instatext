import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//Janardhann Maithil 10-Feb-2015
public class InstaText {

	private static List<String> m_arrString;
	private static int[] m_arrFailure;

	public static void main(String[] args) {
		try {
			InputStream l_ObjinputStream = System.in;
			OutputStream l_ObjoutputStream = System.out;
			BufferedReader l_ObjbufferedReader = new BufferedReader(new InputStreamReader(l_ObjinputStream));
			PrintWriter l_Objpw = new PrintWriter(l_ObjoutputStream);
			String l_StrInputString = "";
			String l_StrTrimmedString = "";

			l_StrInputString = l_ObjbufferedReader.readLine();
			l_StrTrimmedString = l_StrInputString.trim();
			// l_Objpw.write(l_StrTrimmedString);
			m_arrString = new ArrayList<String>(Arrays.asList(l_StrTrimmedString.toLowerCase().split(" ")));

			removePrepositions(m_arrString);
			removeConjuctions(m_arrString);
			removePronouns(m_arrString);
			removeArticles(m_arrString);
			removeModals(m_arrString);
			removeMisc(m_arrString);
			removeSpecialCharacters(m_arrString);

			for (String s : m_arrString) {
				l_Objpw.write("#" + s + " ");
			}
			l_Objpw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void doFinishing(String str) {
		boolean l_isMatchSuccessful = false;
		/*
		 * for (String s : m_arrString) { l_isMatchSuccessful =
		 * doMatch(l_strModals, s); }
		 */
		Iterator<String> iterator = m_arrString.iterator();
		while (iterator.hasNext()) {
			l_isMatchSuccessful = doMatch(str, iterator.next());
			if (l_isMatchSuccessful == true) {
				iterator.remove();
			}
		}

	}

	private static boolean doMatch(String l_strText, String l_strPatter) {

		m_arrFailure = new int[l_strPatter.length()];
		fail(l_strPatter);
		// find match
		int pos = posMatch(l_strText, l_strPatter);
		if (pos == -1)
			return false;
		else
			return true;

	}

	private static int posMatch(String l_strText, String l_strPatter) {

		int i = 0, j = 0;
		int lens = l_strText.length();
		int lenp = l_strPatter.length();
		while (i < lens && j < lenp) {
			if (l_strText.charAt(i) == l_strPatter.charAt(j)) {
				i++;
				j++;
			} else if (j == 0)
				i++;
			else
				j = m_arrFailure[j - 1] + 1;
		}
		return ((j == lenp) ? (i - lenp) : -1);

	}

	private static void fail(String l_strPatter) {

		int n = l_strPatter.length();
		m_arrFailure[0] = -1;
		for (int j = 1; j < n; j++) {
			int i = m_arrFailure[j - 1];
			while ((l_strPatter.charAt(j) != l_strPatter.charAt(i + 1)) && i >= 0)
				i = m_arrFailure[i];
			if (l_strPatter.charAt(j) == l_strPatter.charAt(i + 1))
				m_arrFailure[j] = i + 1;
			else
				m_arrFailure[j] = -1;
		}
	}

	private static void removeModals(List<String> m_arrString) {

		String l_strModals = "cancouldmaymightmustneedoughtshallshouldwillwould";
		doFinishing(l_strModals);
	}

	private static void removeArticles(List<String> m_arrString) {
		String l_strArticles = "anthe";
		doFinishing(l_strArticles);
	}

	private static void removePronouns(List<String> m_arrString) {
		String l_strPronouns = "whilewhomwhichwhosethisthattheirtheythemsherhimyourmeminemyselfwhenwhatwhere";
		doFinishing(l_strPronouns);
	}

	private static void removeConjuctions(List<String> m_arrString) {
		String l_strConjections = "andbutnorforyetsoalthoughbecausesinceunless";
		doFinishing(l_strConjections);
	}

	private static void removePrepositions(List<String> m_arrString) {
		String l_strPrepositions = "aboardaboutaboveacrossafteragainstalongamidamongantiaroundasatbeforebehindbelowbeneathbesidesbetweenbeyondbutbyconcerningconsideringdespitedownduringexceptingexcludingfollowingforfrominsideintolikeminusnearoffontooppositeoutsideoverpastperplusregardingroundsavesincethanthroughtotowardtowardsunderunderneathunlikeuntilupuponversusviawithwithinwithout";
		doFinishing(l_strPrepositions);
	}

	private static void removeSpecialCharacters(List<String> m_arrString2) {
		String l_strSpecialCharacters = "~`!@#$%^&*()_+=-{}[]||;:'<>?,./";
		doFinishing(l_strSpecialCharacters);

	}

	private static void removeMisc(List<String> m_arrString2) {
		String l_strMisc = "hashaveisamarewaswerenotifelseso";
		doFinishing(l_strMisc);

	}
}
