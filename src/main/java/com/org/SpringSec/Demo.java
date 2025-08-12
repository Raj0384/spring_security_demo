package com.org.SpringSec;

public class Demo {
	
	public static String convertAmountToWords(String amountToConvert) {
        // Remove commas from the input
        amountToConvert = amountToConvert.replaceAll(",", "");

        // Parse string to double
        double amount;
        try {
            amount = Double.parseDouble(amountToConvert);
        } catch (NumberFormatException e) {
            return "Incorrect amount !!!";
        }

        if (amount == 0) {
            return "Zero Rupees only";
        }

        int wholeNumberPart = (int) amount;
        int fractionPart = (int) Math.round((amount - wholeNumberPart) * 100); // Extract paise

        String amountInWords = convertToNumber(wholeNumberPart);
        String paiseInWords = fractionPart > 0 ? " and " + convertToNumber(fractionPart) + " Paise" : "";

        return "Rupees " + amountInWords + paiseInWords;
    }

    private static String convertToNumber(int number) {
        String[] units = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

        if (number == 0) return "Zero";
        if (number < 20) return units[number];
        if (number < 100) return tens[number / 10] + (number % 10 != 0 ? " " + units[number % 10] : "");

        if (number < 1000) return units[number / 100] + " Hundred" + (number % 100 != 0 ? " and " + convertToNumber(number % 100) : "");
        if (number < 100000) return convertToNumber(number / 1000) + " Thousand" + (number % 1000 != 0 ? " " + convertToNumber(number % 1000) : "");
        if (number < 10000000) return convertToNumber(number / 100000) + " Lakh" + (number % 100000 != 0 ? " " + convertToNumber(number % 100000) : "");
        
        return convertToNumber(number / 10000000) + " Crore" + (number % 10000000 != 0 ? " " + convertToNumber(number % 10000000) : "");
    }
	
	public static void main(String[] args) {
		String amount = "5,64,665"; // Example input
        System.out.println(convertAmountToWords(amount));
	}

}
