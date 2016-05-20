/*
 * Copyright 2014 - 2016 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

import de.mrapp.android.dialog.decorator.HeaderDialogDecorator;
import de.mrapp.android.dialog.model.HeaderDialog;
import de.mrapp.android.util.ThemeUtil;

/**
 * An abstract base class for all dialogs, which are designed according to Android 5's Material
 * Design guidelines even on pre-Lollipop devices and may contain a header.
 *
 * @author Michael Rapp
 * @since 3.2.0
 */
public abstract class AbstractHeaderDialog extends AbstractMaterialDialog implements HeaderDialog {

    /**
     * An abstract base class for all builders, which allow to create and show dialogs, which are
     * designed according to Android 5's Material Design guidelines even on pre-Lollipop devices and
     * may contain a header.
     *
     * @param <DialogType>
     *         The type of the dialog, which is created by the builder
     * @param <BuilderType>
     *         The type of the builder
     */
    public static abstract class AbstractBuilder<DialogType extends AbstractHeaderDialog, BuilderType extends AbstractBuilder<DialogType, ?>>
            extends AbstractMaterialDialog.AbstractBuilder<DialogType, BuilderType> {

        /**
         * Obtains, whether the dialog's header should be shown, or not, from a specific theme.
         *
         * @param themeResourceId
         *         The resource id of the theme, the visibility should be obtained from, as an
         *         {@link Integer} value
         */
        private void obtainHeaderVisibility(@StyleRes final int themeResourceId) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(themeResourceId,
                    new int[]{R.attr.materialDialogShowHeader});
            showHeader(typedArray.getBoolean(0, false));
        }

        /**
         * Obtains the height of the dialog's header from a specific theme.
         *
         * @param themeResourceId
         *         The resource id of the theme, the height should be obtained from, as an {@link
         *         Integer} value
         */
        private void obtainHeaderHeight(@StyleRes final int themeResourceId) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(themeResourceId,
                    new int[]{R.attr.materialDialogHeaderHeight});
            int defaultHeight =
                    getContext().getResources().getDimensionPixelSize(R.dimen.dialog_header_height);
            setHeaderHeight(typedArray.getDimensionPixelSize(0, defaultHeight));
        }

        /**
         * Obtains the background of the dialog's header from a specific theme.
         *
         * @param themeResourceId
         *         The resource id of the theme, the background should be obtained from, as an
         *         {@link Integer} value
         */
        private void obtainHeaderBackground(@StyleRes final int themeResourceId) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(themeResourceId,
                    new int[]{R.attr.materialDialogHeaderBackground});
            int color = typedArray.getColor(0, -1);

            if (color != -1) {
                setHeaderBackgroundColor(color);
            } else {
                int resourceId = typedArray.getResourceId(0, 0);

                if (resourceId != 0) {
                    setHeaderBackground(resourceId);
                } else {
                    setHeaderBackgroundColor(
                            ThemeUtil.getColor(getContext(), themeResourceId, R.attr.colorPrimary));
                }
            }
        }

        /**
         * Obtains the icon of the dialog's header from a specific theme.
         *
         * @param themeResourceId
         *         The resource id of the theme, the icon should be obtained from, as an {@link
         *         Integer} value
         */
        private void obtainHeaderIcon(@StyleRes final int themeResourceId) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(themeResourceId,
                    new int[]{R.attr.materialDialogHeaderIcon});
            int resourceId = typedArray.getResourceId(0, 0);

            if (resourceId != 0) {
                setHeaderIcon(resourceId);
            }
        }

        /**
         * Obtains the color of the divider of the dialog's header from a specific theme.
         *
         * @param themeResourceId
         *         The resource id of the theme, the color should be obtained from, as an {@link
         *         Integer} value
         */
        private void obtainHeaderDividerColor(@StyleRes final int themeResourceId) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(themeResourceId,
                    new int[]{R.attr.materialDialogHeaderDividerColor});
            int defaultColor = ContextCompat.getColor(getContext(), R.color.header_divider_color);
            setHeaderDividerColor(typedArray.getColor(0, defaultColor));
        }

        /**
         * Obtains, whether the divider of the dialog's header should be shown, or not, from a
         * specific theme.
         *
         * @param themeResourceId
         *         The resource id of the theme, the visibility should be obtained from, as an
         *         {@link Integer} value
         */
        private void obtainShowHeaderDivider(@StyleRes final int themeResourceId) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(themeResourceId,
                    new int[]{R.attr.materialDialogShowHeaderDivider});
            showHeaderDivider(typedArray.getBoolean(0, true));
        }

        /**
         * Creates a new builder, which allows to create dialogs, which are designed according to
         * Android 5's Material Design guidelines even on pre-Lollipop devices and may contain a
         * header.
         *
         * @param context
         *         The context, which should be used by the builder, as an instance of the class
         *         {@link Context}. The context may not be null
         */
        public AbstractBuilder(@NonNull final Context context) {
            super(context);
        }

        /**
         * Creates a new builder, which allows to create dialogs, which are designed according to
         * Android 5's Material Design guidelines even on pre-Lollipop devices and may contain a
         * header.
         *
         * @param context
         *         The context, which should be used by the builder, as an instance of the class
         *         {@link Context}. The context may not be null
         * @param themeResourceId
         *         The resource id of the theme, which should be used by the dialog, as an {@link
         *         Integer} value. The resource id must correspond to a valid theme
         */
        public AbstractBuilder(@NonNull final Context context,
                               @StyleRes final int themeResourceId) {
            super(context, themeResourceId);
        }

        /**
         * Sets, whether the header of the dialog, which is created by the builder, should be shown,
         * or not.
         *
         * @param show
         *         True, if the header of the dialog should be shown, false otherwise
         * @return The builder, the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder showHeader(final boolean show) {
            getDialog().showHeader(show);
            return self();
        }

        /**
         * Sets the height of the header of the dialog, which is created by the builder.
         *
         * @param height
         *         The height, which should be set, in pixels as an {@link Integer} value. The
         *         height must be at least 0
         * @return The builder, the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder setHeaderHeight(final int height) {
            getDialog().setHeaderHeight(height);
            return self();
        }

        /**
         * Sets the background color of the header of the dialog, which is created by the builder.
         *
         * @param color
         *         The background color, which should be set, as an {@link Integer} value
         * @return The builder, the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder setHeaderBackgroundColor(@ColorInt final int color) {
            getDialog().setHeaderBackground(new ColorDrawable(color));
            return self();
        }

        /**
         * Sets the background of the header of the dialog, which is created by the builder.
         *
         * @param resourceId
         *         The resource id of the background, which should be set, as an {@link Integer}
         *         value. The resource id must correspond to a valid drawable resource
         * @return The builder, the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder setHeaderBackground(@DrawableRes final int resourceId) {
            getDialog().setHeaderBackground(ContextCompat.getDrawable(getContext(), resourceId));
            return self();
        }

        /**
         * Sets the background of the header of the dialog, which is created by the builder.
         *
         * @param background
         *         The background, which should be set, as an instance of the class {@link
         *         Drawable}. The background may not be null
         * @return The builder, the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder setHeaderBackground(@NonNull final Drawable background) {
            getDialog().setHeaderBackground(background);
            return self();
        }

        /**
         * Sets the icon of the header of the dialog, which is created by the builder.
         *
         * @param resourceId
         *         The resource id of the icon, which should be set, as an {@link Integer} value.
         *         The resource id must correspond to a valid drawable resource
         * @return The builder, the method has been called upon, as an instance of class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder setHeaderIcon(@DrawableRes final int resourceId) {
            getDialog().setHeaderIcon(ContextCompat.getDrawable(getContext(), resourceId));
            return self();
        }

        /**
         * Sets the icon of the header of the dialog, which is created by the builder.
         *
         * @param icon
         *         The icon, which should be set, as an instance of the class {@link Drawable} or
         *         null, if no icon should be set
         * @return The builder, the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder setHeaderIcon(@Nullable final Drawable icon) {
            getDialog().setHeaderIcon(icon);
            return self();
        }

        /**
         * Sets the color of the divider of the header of the dialog, which is created by the
         * builder.
         *
         * @param color
         *         The color, which should be set, as an {@link Integer} value
         * @return The builder, the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder setHeaderDividerColor(@ColorInt final int color) {
            getDialog().setHeaderDividerColor(color);
            return self();
        }

        /**
         * Sets, wehther the divider of the header of the dialog, which is created by the builder,
         * should be shown, or not.
         *
         * @param show
         *         True, if the divider of the dialog's header should be shown, false otherwise
         * @return The builder the method has been called upon, as an instance of the class {@link
         * AbstractBuilder}
         */
        public final AbstractBuilder showHeaderDivider(final boolean show) {
            getDialog().showHeaderDivider(show);
            return self();
        }

        @CallSuper
        @Override
        protected void obtainStyledAttributes(@StyleRes final int themeResourceId) {
            super.obtainStyledAttributes(themeResourceId);
            obtainHeaderHeight(themeResourceId);
            obtainHeaderBackground(themeResourceId);
            obtainHeaderIcon(themeResourceId);
            obtainHeaderDividerColor(themeResourceId);
            obtainShowHeaderDivider(themeResourceId);
        }

    }

    /**
     * The decorator, which is used by the dialog.
     */
    private final HeaderDialogDecorator decorator;

    /**
     * Creates a dialog, which is designed according to Android 5's Material Design guidelines even
     * on pre-Lollipop devices and may contain a header.
     *
     * @param context
     *         The context, which should be used by the dialog, as an instance of the class {@link
     *         Context}. The context may not be null
     * @param themeResourceId
     *         The resource id of the theme, which should be used by the dialog, as an {@link
     *         Integer} value. The resource id must correspond to a valid theme
     */
    protected AbstractHeaderDialog(@NonNull final Context context,
                                   @StyleRes final int themeResourceId) {
        super(context, themeResourceId);
        this.decorator = new HeaderDialogDecorator(this);
    }

    @Override
    public final boolean isHeaderShown() {
        return decorator.isHeaderShown();
    }

    @Override
    public final void showHeader(final boolean show) {
        decorator.showHeader(show);
    }

    @Override
    public final int getHeaderHeight() {
        return decorator.getHeaderHeight();
    }

    @Override
    public final void setHeaderHeight(final int height) {
        decorator.setHeaderHeight(height);
    }

    @Override
    public final Drawable getHeaderBackground() {
        return decorator.getHeaderBackground();
    }

    @Override
    public final void setHeaderBackgroundColor(@ColorInt final int color) {
        decorator.setHeaderBackgroundColor(color);
    }

    @Override
    public final void setHeaderBackground(@DrawableRes final int resourceId) {
        decorator.setHeaderBackground(resourceId);
    }

    @Override
    public final void setHeaderBackground(@NonNull final Drawable background) {
        decorator.setHeaderBackground(background);
    }

    @Override
    public final Drawable getHeaderIcon() {
        return decorator.getHeaderIcon();
    }

    @Override
    public final void setHeaderIcon(@DrawableRes final int resourceId) {
        setHeaderIcon(ContextCompat.getDrawable(getContext(), resourceId));
    }

    @Override
    public final void setHeaderIcon(@Nullable final Drawable icon) {
        decorator.setHeaderIcon(icon);
    }

    @Override
    public final int getHeaderDividerColor() {
        return decorator.getHeaderDividerColor();
    }

    @Override
    public final void setHeaderDividerColor(@ColorInt final int color) {
        decorator.setHeaderDividerColor(color);
    }

    @Override
    public final boolean isHeaderDividerShown() {
        return decorator.isHeaderDividerShown();
    }

    @Override
    public final void showHeaderDivider(final boolean show) {
        decorator.showHeaderDivider(show);
    }

    @CallSuper
    @Override
    protected void onAttachDecorators(@NonNull final View view) {
        super.onAttachDecorators(view);
        decorator.attach(view);

    }

    @CallSuper
    @Override
    protected void onDetachDecorators() {
        super.onDetachDecorators();
        decorator.detach();
    }

}