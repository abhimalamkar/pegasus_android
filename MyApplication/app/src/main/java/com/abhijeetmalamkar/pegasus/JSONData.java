package com.abhijeetmalamkar.pegasus;

public class JSONData {
    public static String BOOKKEEPING_PRICES = "{\n" +
            "\t\"version\": \"1.0\",\n" +
            "\t\"services\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"plan_title\": \"Bronze\",\n" +
            "\t\t\t\"cost\": 150,\n" +
            "\t\t\t\"additional\": \"1-2 Bank Accounts, 1 Credit Card Account\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"plan_title\": \"Gold\",\n" +
            "\t\t\t\"cost\": 250,\n" +
            "\t\t\t\"additional\": \"2-3 Bank Accounts, 2 Credit Card Accounts\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"plan_title\": \"Platinum\",\n" +
            "\t\t\t\"cost\": 350,\n" +
            "\t\t\t\"additional\": \"4 Bank Accounts, 3 Credit Card Accounts\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"plan_title\": \"Diamond\",\n" +
            "\t\t\t\"cost\": 500,\n" +
            "\t\t\t\"additional\": \"5 Bank Accounts, 4 Credit Card Accounts\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}\n";

    public static String SERVICES = "{\n" +
            "\t\"version\": \"1.0\",\n" +
            "\t\"sections\": [\n" +
            "\t\t{\n" +
            "                 \"title\":\"Business Returns\",\n" +
            "                 \"service\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"\u200BForm 1120 - Corporate\",\n" +
            "\t\t\t\t\t\"cost\": 650\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 1120S - S-Corporation\",\n" +
            "\t\t\t\t\t\"cost\": 650\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 1065 - Partnership\",\n" +
            "\t\t\t\t\t\"cost\": 550\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 706, 709, 1041 - Estate, Gift, Fiduciary\",\n" +
            "\t\t\t\t\t\"cost\": 450\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 990 - Tax Exempt Organization\",\n" +
            "\t\t\t\t\t\"cost\": 600\n" +
            "\t\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"title\":\"Individual Returns\",\n" +
            "                 \"service\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule 1040A\",\n" +
            "\t\t\t\t\t\"cost\": 350\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule 1040 - Basic (Standard Deduction)\",\n" +
            "\t\t\t\t\t\"cost\": 250\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule 1040 - Including Schedules A & B\",\n" +
            "\t\t\t\t\t\"cost\": 370\n" +
            "\t\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"title\":\"Other Returns\",\n" +
            "                 \"service\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"State Return\",\n" +
            "\t\t\t\t\t\"cost\": 100\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Extension of Time to File\",\n" +
            "\t\t\t\t\t\"cost\": 50\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Installment Agreement Request\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 1045 - Application for Tentative Refund\",\n" +
            "\t\t\t\t\t\"cost\": 150\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Amended Returns (fees for additional forms may apply)\",\n" +
            "\t\t\t\t\t\"cost\": 350\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule K-1\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 4506 - Request for Copy of Tax Return\",\n" +
            "\t\t\t\t\t\"cost\": 35\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 4506-T - Request for Transcript\",\n" +
            "\t\t\t\t\t\"cost\": 35\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 2106 - Employee Business Expense\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 8283 - Non-Cash Contributions\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 4684 - Casualty Loss\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 3903 - Moving Expense\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 2441 - Child and Dependent Care\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Home Office Computation\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule C (each)\",\n" +
            "\t\t\t\t\t\"cost\": 250\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule D (first 5 transactions)\",\n" +
            "\t\t\t\t\t\"cost\": 30\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule E - Supplemental Income (first rental)\",\n" +
            "\t\t\t\t\t\"cost\": 150\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule F (each)\",\n" +
            "\t\t\t\t\t\"cost\": 150\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule M\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Schedule SE - Self-Employment Tax\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 4562 - Depreciation & Amortization\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Depreciation Schedule (each item)\",\n" +
            "\t\t\t\t\t\"cost\": 5\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 4797 - Sales of Business Property\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 6252 - Installment Sale (1st Year)\",\n" +
            "\t\t\t\t\t\"cost\": 50\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 6198 - At-Risk Limitation\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 6251 - Alternative Minimum Tax\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 4952 - Investment Interest Expense\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 2555 - Foreign Income\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 1116 - Foreign Tax Credit\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 8824 - Like-Kind Exchanges\",\n" +
            "\t\t\t\t\t\"cost\": 50\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 5405 - First-Time Home Buyers Credit\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Home Sale Worksheets\",\n" +
            "\t\t\t\t\t\"cost\": 25\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Education Credits\",\n" +
            "\t\t\t\t\t\"cost\": 15\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"form\": \"Form 8958 - Allocation of Tax / Community Property States\",\n" +
            "\t\t\t\t\t\"cost\": 30\n" +
            "\t\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}\n";
}
