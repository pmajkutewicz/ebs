package pl.pamsoft.ebs.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

class FrequencyBucketService {

	private enum FrequencyBuckets {
		A(Float.MIN_VALUE, 0.1f, "0.0 - 0.1"),
		B(0.2f, 0.3f, "0.2 - 0.3"),
		C(0.4f, 0.6f, "0.4 - 0.6"),
		D(0.7f, 0.8f, "0.7 - 0.8"),
		E(0.9f, 1.1f, "0.9 - 1.1"),
		F(1.2f, 1.3f, "1.2 - 1.3"),
		G(1.4f, 1.6f, "1.4 - 1.6"),
		H(1.7f, 1.8f, "1.7 - 1.8"),
		I(1.9f, 2.1f, "1.9 - 2.1"),
		J(2.2f, Float.MAX_VALUE, "2.2 ...");

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
		for (FrequencyBuckets frequencyBuckets : FrequencyBuckets.values()) {
			boolean isGreaterThanMin = Float.compare(frequencyBuckets.min, value) == 1;
			boolean isSmallerThanMax = Float.compare(value, frequencyBuckets.max) == -1;
			if (isGreaterThanMin && isSmallerThanMax) {
				return frequencyBuckets.getBucketLabel();
			}
		}
		return "";
	}

	static Collection<String> getAllBuckets() {
		return Arrays.stream(FrequencyBuckets.values()).map(FrequencyBuckets::getBucketLabel).collect(Collectors.toSet());
	}
}
