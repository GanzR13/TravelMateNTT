package com.example.travelmatentt.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.travelmatentt.R

class SearchBarEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        // Atur properti dasar EditText
        setupSearchBar()

        // Listener fokus untuk mengatur seleksi dan keyboard
        setupSearchFocusListener()
    }

    private fun setupSearchBar() {
        // Atur hint
        hint = context.getString(R.string.search) // Pastikan ada string "Cari destinasi..." di `strings.xml`

        // Atur warna hint
        setHintTextColor(ContextCompat.getColor(context, R.color.green))

        // Atur input type
        inputType = InputType.TYPE_CLASS_TEXT

        // Atur action button keyboard
        imeOptions = EditorInfo.IME_ACTION_SEARCH

        // Tambahkan drawable di sebelah kiri
        setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(context, R.drawable.search_24dp),
            null, null, null
        )

        compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.icon_text_padding)

        // Tambahkan listener teks berubah
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchQuery = s.toString()
                performSearch(searchQuery)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Tambahkan listener tombol keyboard
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun setupSearchFocusListener() {
        setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                // Saat EditText difokuskan
                (view as EditText).setSelection(view.text.length)
            } else {
                // Saat fokus hilang
                hideKeyboard()
            }
        }
    }

    private fun performSearch(query: String) {
        // Logika pencarian dapat dipanggil melalui listener atau delegasi
        when {
            query.isEmpty() -> resetSearchResults()
            query.length >= 2 -> searchDestinations(query)
        }
    }

    private fun resetSearchResults() {
        // Implementasi reset (akan diatur di aktivitas atau fragment)
    }

    private fun searchDestinations(query: String) {
        // Implementasi pencarian (akan diatur di aktivitas atau fragment)
    }

    private fun hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}