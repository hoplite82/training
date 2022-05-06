package hack.array_recursion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

class Input {
	int[] game;
	int leap;

	public Input(int[] game, int leap) {
		this.game = game;
		this.leap = leap;
	}
}

@RunWith(Parameterized.class)
public class SolutionTest {
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;

	private ByteArrayInputStream testIn;
	private ByteArrayOutputStream testOut;

	@Parameter(0)
	public int[] game_array;

	@Parameter(1)
	public int leap;

	@Parameter(2)
	public String expected;

	@Parameters(name = "index: {index}: leap: {1} expected; {2} ")
	public static Object[][] data() throws IOException {
		List<Input> inputs = getInputs();
		List<String> expected = getExpected();
		int numberofcasesin = inputs.size();
		int numberofcasesex = expected.size();

		// System.out.println("casesin:" + numberofcasesin + " casesout" +
		// numberofcasesex);
		Object[][] testcasedata = new Object[numberofcasesin][3];
		for (int i = 0; i < testcasedata.length; i++) {
			Input in = inputs.get(i);
			testcasedata[i][0] = in.game;
			testcasedata[i][1] = in.leap;
			testcasedata[i][2] = expected.get(i);

		}

		return testcasedata;
	}

	public static List<Input> getInputs() throws FileNotFoundException {
		List<Input> inputs = new ArrayList<>();
		Scanner scan = new Scanner(new FileInputStream(new File("testdata/hack_arraygame/input03.txt")));
		int q = scan.nextInt();
		while (q-- > 0) {
			int n = scan.nextInt();
			int leap = scan.nextInt();

			int[] game = new int[n];
			for (int i = 0; i < n; i++) {
				game[i] = scan.nextInt();
			}
			Input casein = new Input(game, leap);
			inputs.add(casein);

		}
		scan.close();
		return inputs;
	}

	private static List<String> getExpected() throws FileNotFoundException {
		List<String> expected = new ArrayList<>();
		Scanner scan2 = new Scanner(new FileInputStream(new File("testdata/hack_arraygame/output03.txt")));
		expected = new ArrayList<String>();
		while (scan2.hasNextLine()) {
			expected.add(scan2.nextLine());
		}
		return expected;
	}

	@Before
	public void setUpOutput() throws IOException {
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));

	}

	private void provideInput(String data) {
		testIn = new ByteArrayInputStream(data.getBytes());
		System.setIn(testIn);
	}

	private String getOutput() {
		return testOut.toString().trim();
	}

	@After
	public void restoreSystemInputOutput() {
		System.setIn(systemIn);
		System.setOut(systemOut);
	}

	int printquantity = 1;

	@Test
	public void testCanWin() throws IOException {

		String actual = Solution.canWin(leap, game_array) ? "YES" : "NO";
		try {

			assertEquals(expected, actual);
		} catch (AssertionError e) {
			StringBuilder game_string = new StringBuilder(
					expected + "--" + actual + "\n" + game_array.length + " " + leap + "\n");
			StringBuilder game_string_file = new StringBuilder(game_array.length + " " + leap + "\n");
			for (int i = 0; i < game_array.length; i++) {
				if (i < 10)
					game_string.append(game_array[i]).append(" ");
				else if (i < 100)
					game_string.append(" ").append(game_array[i]).append(" ");
				if (i < 10)
					game_string_file.append(game_array[i]).append(" ");
				else if (i < 100)
					game_string_file.append(" ").append(game_array[i]).append(" ");

			}
			game_string.append("\n");
			game_string_file.append("\n");
			for (int i = 0; i < game_array.length; i++) {
				game_string.append(i).append(" ");
				game_string_file.append(i).append(" ");
			}
			if (printquantity > 0) {
				Files.write(Path.of("testdata/hack_arraygame/inunit.txt"), game_string_file.toString().getBytes(),
						StandardOpenOption.CREATE, StandardOpenOption.WRITE);
				printquantity--;
			}
			fail(game_string.toString());
			
		}
	}

//	@Test 
//	public void testSolution() throws IOException {
//		
//		Path pathtoexpected = Path.of("testdata/hack_arraygame/out2.txt");
//		final String expected = Files.readString(pathtoexpected);
//		Solution.main(new String[0]);
//
//		assertEquals(expected, getOutput());
//	}
}