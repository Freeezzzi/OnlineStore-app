package ru.freeezzzi.coursework.onlinestore.ui.mainpage.recipes.singlerecipe

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import ru.freeezzzi.coursework.onlinestore.R
import ru.freeezzzi.coursework.onlinestore.databinding.RecipeFragmentBinding
import ru.freeezzzi.coursework.onlinestore.domain.models.Ingredient
import ru.freeezzzi.coursework.onlinestore.domain.models.Product
import ru.freeezzzi.coursework.onlinestore.domain.models.Recipe
import ru.freeezzzi.coursework.onlinestore.domain.models.Step
import ru.freeezzzi.coursework.onlinestore.ui.BaseFragment
import ru.freeezzzi.coursework.onlinestore.ui.mainpage.cart.CartViewModel
import ru.freeezzzi.coursework.onlinestore.ui.setPicture

class SingleRecipeFragment : BaseFragment(R.layout.recipe_fragment) {
    private val binding: RecipeFragmentBinding by viewBinding(RecipeFragmentBinding::bind)

    private val cartViewModel: CartViewModel by activityViewModels()

    private val args: SingleRecipeFragmentArgs by navArgs()

    private val ingredientsListAdapter = IngredientsListAdapter(
        itemOnClickAction = {},
        addItemAction = ::addItemAction,
        removeItemAction = ::removeItemAction
    )

    private val stepsListAdapter = StepsListAdapter()

    override fun initViews(view: View) {
        super.initViews(view)

        // toolbar
        binding.recipeToolbar.toolbarTitle.text = getString(R.string.recipe)
        binding.recipeToolbar.toolbarBackButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        // labels
        var difficulty = ""
        if (args.recipe.difficulty == Recipe.DIFFICULTY_EASY) difficulty = binding.root.resources.getString(R.string.easy)
        if (args.recipe.difficulty == Recipe.DIFFICULTY_MEDIUM) difficulty = binding.root.resources.getString(R.string.medium)
        if (args.recipe.difficulty == Recipe.DIFFUCULTY_HARD) difficulty = binding.root.resources.getString(R.string.hard)
        binding.recipeDiffAndTime.text = difficulty + "," + args.recipe.cookingTime + "m"
        binding.recipeName.text = args.recipe.name
        setPicture(args.recipe.imageUrl)

        binding.recipeIngredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeIngredientsRecyclerview.adapter = ingredientsListAdapter
        binding.recipeIngredientsRecyclerview.isNestedScrollingEnabled = false

        val ingredients = mutableListOf<Ingredient>()
        args.recipe.ingredientNames.forEachIndexed { index, s ->
            cartViewModel.isInCart(args.recipe.ingredientProducts.get(index))
            ingredients.add(
                Ingredient(
                    name = s,
                    count = args.recipe.ingredientCount.get(index),
                    product = args.recipe.ingredientProducts.get(index)
                )
            )
        }
        ingredientsListAdapter.submitList(ingredients)

        binding.recipeStepsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeStepsRecyclerView.adapter = stepsListAdapter
        binding.recipeStepsRecyclerView.isNestedScrollingEnabled = false

        val steps = mutableListOf<Step>()
        args.recipe.stepsInfo.forEachIndexed { index, s ->
            steps.add(
                Step(
                    stepImage = args.recipe.stepsImages.get(index),
                    stepInfo = s
                )
            )
        }
        stepsListAdapter.submitList(steps)
    }

    fun addItemAction(product: Product) {
        cartViewModel.addOneItem(product)
        ingredientsListAdapter.notifyDataSetChanged()
    }

    fun removeItemAction(product: Product) {
        cartViewModel.removeOneItem(product)
        ingredientsListAdapter.notifyDataSetChanged()
    }

    // Для закругленяи краев картинки
    fun setPicture(imageUrl: String) {
        val transformation: Transformation
        val dimension = binding.recipeImage.resources.getDimension(R.dimen.rounded_corner_rad)
        val cornerRadius = dimension.toInt()
        transformation = RoundedCornersTransformation(cornerRadius, 0)
        if (imageUrl.isEmpty()) {
            binding.recipeImage.setImageResource(R.color.white)
        } else {
            Picasso.get().isLoggingEnabled = true
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(transformation)
                .fit()
                .centerInside()
                // .centerCrop()
                .into(binding.recipeImage)
        }
    }
}
