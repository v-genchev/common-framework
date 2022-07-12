package helpers;

import org.apache.commons.lang3.StringUtils;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VariableTransformer {

    private static final String defaultDateFormat = "MM/dd/yyyy";
    private VariableTransformer() {

    }

    public static String transformSingleValue(String singleValue){
        return transformSingleValue(singleValue, defaultDateFormat);
    }

    public static String transformSingleValue(String singleValue, String dateFormat){
        String transformedValue = singleValue;
        if(singleValue != null) {
            Pattern variablePattern = Pattern.compile("(.*?)_(Config|PrettyDate)_\\[(.*?)](.*)");
            Matcher matcher = variablePattern.matcher(singleValue);
            if (matcher.find()) {
                String precedingText = StringUtils.defaultString(matcher.group(1));
                String followingText = StringUtils.defaultString(matcher.group(4));
                String getFrom = matcher.group(2);
                String variableToGet = matcher.group(3);
                switch (getFrom) {
                    case "Config":
                        transformedValue = PropertiesCache.getInstance().getProperty(variableToGet);
                        break;
                    case "PrettyDate":
                        transformedValue = transformPrettyDate(variableToGet, dateFormat);
                        break;
                    default:
                        throw new IllegalArgumentException(String.format("No transformation available for prefix: %s", getFrom));
                }
                transformedValue = String.format("%s%s%s", precedingText, transformedValue, followingText);
            }
        }
        return transformedValue;
    }
    public static List<String> transformList(List<String> listValues){
        return transformList(listValues, defaultDateFormat);
    }

    public static List<String> transformList(List<String> listValues, String dateFormat){
        List<String> list = new ArrayList<>();
        for (String item : listValues) {
            String s = VariableTransformer.transformSingleValue(item, dateFormat);
            list.add(s);
        }
        return list;
    }

    public static Map<String, String> transformMap(Map<String, String> mapValues){
        return transformMap(mapValues, defaultDateFormat);
    }

    public static Map<String, String> transformMap(Map<String, String> mapValues, String dateFormat){
        LinkedHashMap<String, String> transformedMap = new LinkedHashMap<>();
        for(Map.Entry<String, String> entry : mapValues.entrySet()){
           transformedMap.put(transformSingleValue(entry.getKey(), dateFormat), transformSingleValue(entry.getValue(), dateFormat));
        }
        return transformedMap;
    }

    private static String transformPrettyDate(String prettyDate, String outputDateFormat){
        PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
        List<Date> dates = prettyTimeParser.parse(prettyDate);
        Date date = dates.get(dates.size()-1);
        DateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
        return outputFormat.format(date);
    }

}
