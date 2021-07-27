package com.elnemr.foody.adapters.base

import androidx.recyclerview.widget.DiffUtil

class DiffCallBack<T>(
    private var oldList: List<T>,
    private var newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}









//
//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
//    private AsyncListDiffer<User> mDiffer;//callBack
//    private DiffUtil.ItemCallback<User> diffCallback = new DiffUtil.ItemCallback<User>() {
//        @Override
//        public boolean areItemsTheSame(User oldItem, User newItem) {
//            return TextUtils.equals(oldItem.getId(), newItem.getId());
//        }
//        @Override
//        public boolean areContentsTheSame(User oldItem, User newItem) {
//            return oldItem.getAge() == newItem.getAge();
//        }
//    };//define AsyncListDiffer
//    public UserAdapter() {
//        mDiffer = new AsyncListDiffer<>(this, diffCallback);
//    }
//    @Override
//    public int getItemCount() {
//        return mDiffer.getCurrentList().size();
//    }//method to submit list
//    public void submitList(List<User> data) {
//        mDiffer.submitList(data);
//    }//method getItem by position
//    public User getItem(int position) {
//        return mDiffer.getCurrentList().get(position);
//    }//override the method of Adapter
//    @NonNull
//    @Override
//    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
//        return new UserViewHolder(itemView);
//    }
//    @Override
//    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
//        holder.setData(getItem(position));
//    }}