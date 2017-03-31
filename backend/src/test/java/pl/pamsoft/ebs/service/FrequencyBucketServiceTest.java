package pl.pamsoft.ebs.service;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FrequencyBucketServiceTest {

	@Test(dataProvider = "testData")
	public void shouldReturnProperLabel(float value, String expectedLabel) throws Exception {
		assertEquals(FrequencyBucketService.getLabelForValue(value), expectedLabel);
	}

	@DataProvider
	Object[][] testData() {
		return new Object[][]{
			{0.0f, "0.0 - 0.1"},
			{0.1f, "0.0 - 0.1"},
			{0.6f, "0.4 - 0.6"},
			{0.6166F, "0.4 - 0.6"},
			{0.6666F, "0.7 - 0.8"},
			{0.7f, "0.7 - 0.8"},
			{1.0f, "0.9 - 1.1"},
			{2.0f, "1.9 - 2.1"},
			{2.2f, "2.2 ..."},
			{5.0f, "2.2 ..."}
		};
	}

}
