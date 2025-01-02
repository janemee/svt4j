public enum TestEnum {

    A("A", "测试A"),
    B("B", "测试B"),
    C("C", "测试C");

    private String url;
    private String desc;

    TestEnum(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public static TestEnum get(String str) {

        for (TestEnum testEnum : TestEnum.values()) {
            if (testEnum.url.contains(str)) {
                return testEnum;
            }
        }
        return A;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }

}
