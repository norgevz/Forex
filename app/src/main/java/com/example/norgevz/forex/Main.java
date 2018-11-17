package com.example.norgevz.forex;

public class Main {
    // Default currency list
    public static final String CURRENCY_LIST[] = {
            "EUR", "USD", "RUB", "JPY"
    };

    // names
    public static final String CURRENCY_NAMES[] = {
            "EUR", "USD", "RUB", "BGN",
            "CZK", "DKK", "GBP", "HUF",
            "PLN", "RON", "SEK", "CHF",
            "NOK", "KRW", "JPY", "TRY",
            "AUD", "BRL", "CAD", "CNY",
            "HKD", "IDR", "ILS", "INR",
            "ISK", "HRK", "MXN", "MYR",
            "NZD", "PHP", "SGD", "THB",
            "ZAR"
    };

    //symbols
    public static final String CURRENCY_SYMBOLS[] = {
            "€", "$", "₽", "лв",
            "Kč", "kr", "£", "Ft",
            "zł", "lei", "kr", "",
            "kr", "₩", "₽", "₺",
            "$", "R$", "$", "¥",
            "$", "Rp", "₪", "₹",
            "kr", "kn", "$", "RM",
            "$", "₱", "$", "฿", "S"
    };
    // fullname
    public static final Integer CURRENCY_LONGNAMES[] = {
            R.string.full_eur, R.string.full_usd, R.string.full_rub,
            R.string.full_bgn, R.string.full_czk, R.string.full_dkk,
            R.string.full_gbp, R.string.full_huf, R.string.full_pln,
            R.string.full_ron, R.string.full_sek, R.string.full_chf,
            R.string.full_nok, R.string.full_krw, R.string.full_jpy,
            R.string.full_try, R.string.full_aud, R.string.full_brl,
            R.string.full_cad, R.string.full_cny, R.string.full_hkd,
            R.string.full_idr, R.string.full_ils, R.string.full_inr,
            R.string.full_isk, R.string.full_hrk, R.string.full_mxn,
            R.string.full_myr, R.string.full_nzd, R.string.full_php,
            R.string.full_sgd, R.string.full_thb, R.string.full_zar
    };

    //  flags
    public static final Integer CURRENCY_FLAGS[] = {
            R.drawable.flag_eur, R.drawable.flag_usd, R.drawable.flag_rub,
            R.drawable.flag_bgn, R.drawable.flag_czk, R.drawable.flag_dkk,
            R.drawable.flag_gbp, R.drawable.flag_huf, R.drawable.flag_pln,
            R.drawable.flag_ron, R.drawable.flag_sek, R.drawable.flag_chf,
            R.drawable.flag_nok, R.drawable.flag_krw, R.drawable.flag_jpy,
            R.drawable.flag_try, R.drawable.flag_aud, R.drawable.flag_brl,
            R.drawable.flag_cad, R.drawable.flag_cny, R.drawable.flag_hkd,
            R.drawable.flag_idr, R.drawable.flag_ils, R.drawable.flag_inr,
            R.drawable.flag_isk, R.drawable.flag_hrk, R.drawable.flag_mxn,
            R.drawable.flag_myr, R.drawable.flag_nzd, R.drawable.flag_php,
            R.drawable.flag_sgd, R.drawable.flag_thb, R.drawable.flag_zar
    };

    private List<String> currencyNameList;
    private List<Integer> flagList;
    private List<String> nameList;
    private List<Integer> fullNameList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Find views
        flagView = findViewById(R.id.flag);
        nameView = findViewById(R.id.name);
        fullNameView = findViewById(R.id.full_name);

        flagList = new ArrayList<>();
        nameList = new ArrayList<>();
        fullNameList = new ArrayList<>();

    }

}
