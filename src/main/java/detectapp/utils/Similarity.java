package detectapp.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Similarity {

    private static final Pattern FEATURE_PATTERN = Pattern.compile("[a-zA-Z0-9$_]+");

    public double get(String methodBodyA, String methodBodyB) {
        List<String> methodAList = getTerms(methodBodyA);
        List<String> methodBList = getTerms(methodBodyB);

        return getCosineSimilarity(getFreqVector(methodAList), getFreqVector(methodBList));
    }

    private List<String> getTerms(final String a) {
        final List<String> termsList = new ArrayList<>();
        final Matcher m = FEATURE_PATTERN.matcher(a);
        while (m.find()) {
            termsList.add(m.group());
        }
        return termsList;
    }

    private Map<String, Integer> getFreqVector(List<String> methodAList) {
        return methodAList.parallelStream().collect(Collectors.groupingBy(str -> str, Collectors.summingInt(str -> 1)));
    }

    private double getCosineSimilarity(Map<String, Integer> freqVectorA, Map<String, Integer> freqVectorB) {
        final double up = freqVectorA.keySet().parallelStream().filter(freqVectorB::containsKey)
                .collect(Collectors.summarizingDouble(key -> freqVectorA.get(key) * freqVectorB.get(key))).getSum();
        final double a = getQuadraticSum(freqVectorA.values());
        final double b = getQuadraticSum(freqVectorB.values());
        return up / Math.sqrt(a * b);
    }

    private double getQuadraticSum(final Collection<Integer> collection) {
        return collection.stream().reduce(0, (result, x) -> result + (x * x));
    }
}
