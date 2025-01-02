public class subTestA implements testStrategy {
    @Override
    public Object getTest() {
        System.out.println("这是A策略");
        return null;
    }

    @Override
    public TestEnum getTestEnum() {
        return TestEnum.A;
    }
}
