package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

// Проверка различных вариантов перемещения ингредиентов
@RunWith(Parameterized.class)
public class BurgerMoveParameterizedTest {

    private Burger burger;
    private final List<Ingredient> initial;
    private final int from;
    private final int to;
    private final List<Ingredient> expected;

    public BurgerMoveParameterizedTest(List<Ingredient> initial, int from, int to, List<Ingredient> expected) {
        this.initial = initial;
        this.from = from;
        this.to = to;
        this.expected = expected;
    }

    @Before
    public void setUp() {
        // Создаем бургер
        burger = TestData.createBurgerWithIngredients(TestData.createBasicBun(), initial);
    }

    //Набор данных для перемещения
    @Parameterized.Parameters(name = "move from {1} to {2} -> {3}")
    public static Iterable<Object[]> scenarios() {
        return Arrays.asList(new Object[][]{
                {TestData.LIST_ABC, 0, 2, TestData.LIST_BCA},
                {TestData.LIST_ABC, 2, 0, TestData.LIST_CAB},
                {TestData.LIST_ABC, 1, 2, TestData.LIST_ACB},
                {TestData.LIST_ABC, 0, 1, TestData.LIST_BAC},
                // Перемещение элемента на свою же позицию (список не меняется)
                {TestData.LIST_ABC, 1, 1, TestData.LIST_ABC},
                // Тест для списка из 2 элементов (A, B) -> (B, A)
                {Arrays.asList(TestData.MOVE_INGREDIENT_A, TestData.MOVE_INGREDIENT_B), 0, 1,
                        Arrays.asList(TestData.MOVE_INGREDIENT_B, TestData.MOVE_INGREDIENT_A)},
                {Arrays.asList(TestData.MOVE_INGREDIENT_A, TestData.MOVE_INGREDIENT_B), 1, 0,
                        Arrays.asList(TestData.MOVE_INGREDIENT_B, TestData.MOVE_INGREDIENT_A)},
        });
    }

    @Test
    public void shouldMoveIngredientCorrectly() {
        burger.moveIngredient(from, to);
        // Проверяем размер списка
        assertEquals("Количество ингредиентов после перемещения не должно измениться",
                initial.size(), burger.ingredients.size());
        // Проверяем порядок элементов
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("Ингредиент на позиции " + i + " должен совпадать с ожидаемым",
                    expected.get(i), burger.ingredients.get(i));
        }
    }
}