package demos.expmind.andromeda.data


class Result<T>(val value: T, val successful: Boolean = true,val errorMessage: String = "")

enum class DownloadStatus {
    LOADING,
    DOWNLOADED
}