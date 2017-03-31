package pl.pamsoft.ebs.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Range;

class FrequencyBucketService {

	private enum FrequencyBuckets {
		A(-99999f, 0.1f, "0.0 - 0.1"),
		B(0.2f, 0.3f, "0.2 - 0.3"),
		C(0.4f, 0.6f, "0.4 - 0.6"),
		D(0.7f, 0.8f, "0.7 - 0.8"),
		E(0.9f, 1.1f, "0.9 - 1.1"),
		F(1.2f, 1.3f, "1.2 - 1.3"),
		G(1.4f, 1.6f, "1.4 - 1.6"),
		H(1.7f, 1.8f, "1.7 - 1.8"),
		I(1.9f, 2.1f, "1.9 - 2.1"),
		J(2.2f, 99999f, "2.2 ...");

		private final float min;
		private final float max;
		private final String bucketLabel;

		FrequencyBuckets(float min, float max, String bucketLabel) {
			this.min = min;
			this.max = max;
			this.bucketLabel = bucketLabel;
		}

		public String getBucketLabel() {
			return bucketLabel;
		}
	}

	static String getLabelForValue(float value) {
		float rounded = new BigDecimal(String.valueOf(value)).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		for (FrequencyBuckets frequencyBuckets : FrequencyBuckets.values()) {
			if (Range.between(frequencyBuckets.min, frequencyBuckets.max).contains(rounded)) {
				return frequencyBuckets.getBucketLabel();
			}
		}
		return String.valueOf(value);
	}

	static Collection<String> getAllBuckets() {
		return Arrays.stream(FrequencyBuckets.values()).map(FrequencyBuckets::getBucketLabel).collect(Collectors.toSet());
	}
}
