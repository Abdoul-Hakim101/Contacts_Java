package contacts;

public enum Gender {
    M, F, UNKNOWN;

    public static Gender fromString(String gender) {
        if (gender == null || gender.isEmpty()) {
            System.out.println("Bad gender!");
            return UNKNOWN;
        }

        return switch (gender.toUpperCase()) {
            case "M" -> M;
            case "F" -> F;
            default -> {
                System.out.println("Bad gender!");
                yield UNKNOWN;
            }
        };
    }
}