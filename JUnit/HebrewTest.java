package com.example.help.test;

import com.example.help.Hebrew;
import com.example.help.R;
import android.widget.TextView;
import android.test.ActivityInstrumentationTestCase2;

public class HebrewTest extends ActivityInstrumentationTestCase2<Hebrew> 
{
	private TextView result;
	
	public HebrewTest() 
	{
		super ("com.example.help",Hebrew.class);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		Hebrew heb = getActivity();
		result = (TextView) heb.findViewById(R.id.result);
	}
	
	private static final String F_RESULT = "a";  
	public void test1() 
	{ 
		
	   String res = result.getText().toString();  
	   assertTrue("Result should be a", res.equals(F_RESULT));  
	} 
	
	private static final String S_RESULT = "b";  
	public void test2() 
	{ 
		
	   String res = result.getText().toString();  
	   assertTrue("Result should be a", res.equals(S_RESULT));  
	} 
}
