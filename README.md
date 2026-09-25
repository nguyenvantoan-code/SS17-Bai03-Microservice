# BÀI TẬP 3: ĐẢM BẢO NHẤT QUÁN DỮ LIỆU VỚI @CACHEEVICT

## 1. Giải thích sự lựa chọn: Vì sao `@CacheEvict` thay vì `@CachePut`
Khi thao tác cập nhật dữ liệu, Spring cung cấp 2 giải pháp:
- `@CachePut`: Lưu trực tiếp kết quả mới (sau khi tính toán hoặc lưu DB) đè lên bộ nhớ cache cũ.
- `@CacheEvict`: Xóa hoàn toàn key đó khỏi bộ nhớ cache. Request đọc tiếp theo sẽ gặp Cache Miss, tự động chọc xuống DB lấy dữ liệu mới và tái nạp vào cache (Cache-Aside pattern).

**Tại sao nên ưu tiên `@CacheEvict`? (Phòng tránh Race Condition)**
Nếu hệ thống có độ trễ lớn và hai người dùng cùng cập nhật giá liên tiếp (User A sửa thành 75k, User B sửa thành 80k ngay sau đó):
Nếu dùng `@CachePut`, do vấn đề bất đồng bộ (Network delay, Thread scheduling), lệnh đẩy vào Cache của User A có thể hoàn thành *sau* lệnh đẩy của User B, dẫn tới việc DB lưu 80k (mới nhất do B thao tác), nhưng Cache lại bị đè thành 75k (do A chậm trễ). Dữ liệu hoàn toàn mất nhất quán (Stale Data).

Việc dùng `@CacheEvict` đảm bảo xóa trắng Cache. Luồng đọc tiếp theo chắc chắn lấy từ nguồn chân lý (Database), tránh sai lệch dữ liệu. Do đó, đây là best practice cho hệ thống thực tế.

## 2. Các kết quả kiểm chứng
Sau khi gọi phương thức `updateMenu(Menu menu)`:
- Logic: `menuRepository.save(menu)` được chạy trước.
- Annotation `@CacheEvict(value = "menuCache", key = "#menu.id")` kích hoạt việc xóa key trong Cache.
- Nếu kiểm tra bằng lệnh redis-cli `GET menuCache::1`, kết quả sẽ là `(nil)`.
- Khi gọi lại API `GET /menu/1`, dữ liệu lấy từ DB sẽ là giá mới `75000` thay vì giá cũ `55000`.

*(Đính kèm ảnh minh họa thao tác Redis CLI trả về `(nil)` sau khi xóa)*
